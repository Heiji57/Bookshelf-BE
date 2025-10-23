package plain.bookshelf.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.domain.member.presentation.dto.request.MemberLoginRequestDto;
import plain.bookshelf.global.exception.ErrorCode;
import plain.bookshelf.global.security.entity.RefreshToken;
import plain.bookshelf.global.security.entity.repository.RefreshTokenRepository;
import plain.bookshelf.global.security.jwt.JwtTokenDto;
import plain.bookshelf.global.security.jwt.JwtTokenProvider;

import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Service
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailRepository emailRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Value("${jwt.refresh_token_expiration_time}")
    private Long expirationTime;

    public JwtTokenDto login(MemberLoginRequestDto memberLoginRequestDto) {
        if (memberRepository.findByUserName(memberLoginRequestDto.credential()).isEmpty()
                && emailRepository.findEmailByAddress(memberLoginRequestDto.credential()).isEmpty()) {

            throw new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND);
        }

        // 1. Login ID,EMAIL/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberLoginRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        // authentication 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUserName 메서드가 실행됨
        // 2차 검증
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

        Date now = new Date();
        log.info("user login time: {}", now);

        // 5. 토큰 발급
        return jwtTokenDto;
    }
}
