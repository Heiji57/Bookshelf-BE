package plain.bookshelf.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.email.exception.NotVerificationEmailException;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.UserMemberRepository;
import plain.bookshelf.domain.member.exception.AlreadyAssignedEmailException;
import plain.bookshelf.domain.member.exception.ExistNickNameException;
import plain.bookshelf.domain.member.exception.ExistUserNameException;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.global.security.entity.RefreshToken;
import plain.bookshelf.global.security.entity.repository.RefreshTokenRepository;
import plain.bookshelf.global.security.exception.RefreshValueNotEqualException;
import plain.bookshelf.global.security.exception.logoutUserException;
import plain.bookshelf.domain.member.presentation.dto.MemberDeleteRequestDto;
import plain.bookshelf.domain.member.presentation.dto.MemberSignupRequestDto;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.member.presentation.dto.MemberSignupResponseDto;
import plain.bookshelf.domain.member.presentation.dto.TokenRequestDto;
import plain.bookshelf.global.security.jwt.JwtTokenDto;
import plain.bookshelf.global.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserMemberRepository userMemberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Value("${jwt.expiration_time}")
    private Long expirationTime;

    @Transactional
    public MemberSignupResponseDto signup(MemberSignupRequestDto memberSignupRequestDto) {

        // 잘못된 입력
        // 수정: 이메일 인증을 위해 DB에 저장된 이메일이 없다면 예외 발생
        Email email = emailRepository.findEmailByAddress(memberSignupRequestDto.getAddress())
                .orElseThrow(() -> new NotVerificationEmailException(memberSignupRequestDto.getAddress()));

        // 1. 사용자명, 닉네임 중복 체크
        if (userMemberRepository.existsByUserName(memberSignupRequestDto.getUserName())) {
            throw new ExistUserNameException(memberSignupRequestDto.getUserName());
        }
        if (userMemberRepository.existsByNickName(memberSignupRequestDto.getNickName())) {
            throw new ExistNickNameException(memberSignupRequestDto.getNickName());
        }
        if (email.getMember() != null) {
            throw new AlreadyAssignedEmailException(memberSignupRequestDto.getAddress());
        }
        if (!email.isVerified()) {
            throw new NotVerificationEmailException(memberSignupRequestDto.getAddress());
        }

        // 3. Member 객체 생성
        Member member = Member.builder()
                .userName(memberSignupRequestDto.getUserName())
                .nickName(memberSignupRequestDto.getNickName())
                .password(passwordEncoder.encode(memberSignupRequestDto.getPassword()))
                .authority(Member.Authority.ROLE_USER)
                .build();


            email.setMember(member);
            member.getEmails().add(email);
            emailRepository.save(email);

        // 5. DB 저장
        Member savedMember = userMemberRepository.save(member);

        // 6. Response DTO 반환
        return MemberSignupResponseDto.of(savedMember);
    }

    public void userDelete (MemberDeleteRequestDto memberDeleteRequestDto) {
        Member member = userMemberRepository.findByUserName(memberDeleteRequestDto.getUserName())
                .orElseThrow(() -> new NotExistUserException("Not exist username: " + memberDeleteRequestDto.getUserName()));
        userMemberRepository.delete(member);
    }

    @Transactional
    public JwtTokenDto login(MemberSignupRequestDto memberSignupRequestDto) {
        // 1. Login ID,EMAIL/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberSignupRequestDto.toAutentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //      authentication 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUserName 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(jwtTokenDto.getRefreshToken())
                .expiration(expirationTime)
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return jwtTokenDto;
    }

    @Transactional
    public JwtTokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. RefreshToken 검증
        if (!jwtTokenProvider.validateRefreshToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh token이 유효하지 않습니다.");
        }

        // 2. Access Token 예시 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new logoutUserException("logout user."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RefreshValueNotEqualException("Not equal to refresh token.");
        }

        // 5. 새로운 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(authentication);

        // 6. 저장소 정보 업데이트 및 TTL 설정
        refreshToken.updateValue(jwtTokenDto.getRefreshToken());
        refreshTokenRepository.save(refreshToken); // Redis에 저장, TTL은 @TimeToLive 어노테이션으로 자동 설정됨

        // 토큰 발급
        return jwtTokenDto;
    }
}
