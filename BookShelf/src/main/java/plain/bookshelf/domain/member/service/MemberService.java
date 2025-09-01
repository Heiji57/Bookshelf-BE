package plain.bookshelf.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.presentation.dto.MemberDeleteRequestDto;
import plain.bookshelf.domain.member.presentation.dto.MemberSignupRequestDto;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.member.presentation.dto.MemberSignupResponseDto;
import plain.bookshelf.global.exception.ExistEmailException;
import plain.bookshelf.global.exception.ExistNickNameException;
import plain.bookshelf.global.exception.ExistUserNameException;
import plain.bookshelf.global.exception.NotExistUserException;
import plain.bookshelf.global.security.entity.repository.RefreshTokenRepository;
import plain.bookshelf.global.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberSignupResponseDto signup(MemberSignupRequestDto memberSignupRequestDto) {

        // 1. 사용자명, 닉네임 중복 체크
        if (memberRepository.existsByUserName(memberSignupRequestDto.getUserName())) {
            throw new ExistUserNameException(memberSignupRequestDto.getUserName());
        }
        if (memberRepository.existsByNickName(memberSignupRequestDto.getNickName())) {
            throw new ExistNickNameException(memberSignupRequestDto.getNickName());
        }

        // 2. 이메일 중복 체크
        if (memberSignupRequestDto.getEmails() != null && !memberSignupRequestDto.getEmails().isEmpty()) {
            if (memberSignupRequestDto.getEmails().stream()
                    .anyMatch(emailRepository::existsByAddress)) {
                throw new ExistEmailException(memberSignupRequestDto.getEmails());
            }
        }

        // 3. Member 객체 생성
        Member member = Member.builder()
                .userName(memberSignupRequestDto.getUserName())
                .nickName(memberSignupRequestDto.getNickName())
                .password(passwordEncoder.encode(memberSignupRequestDto.getPassword()))
                .authority(Member.Authority.ROLE_USER)
                .build();

        // 4. 이메일 객체 생성 및 연관관계 세팅
        if (memberSignupRequestDto.getEmails() != null && !memberSignupRequestDto.getEmails().isEmpty()) {
            memberSignupRequestDto.getEmails().forEach(addr -> {
                Email email = Email.builder()
                        .address(addr)
                        .verified(false)
                        .delivered(memberSignupRequestDto.isEmailDelivered())
                        .build();
                member.addEmail(email);
            });
        }

        // 5. DB 저장
        Member savedMember = memberRepository.save(member);

        // 6. Response DTO 반환
        return MemberSignupResponseDto.of(savedMember);
    }

    public void userDelete (MemberDeleteRequestDto memberDeleteRequestDto) {
        Member member = memberRepository.findByUserName(memberDeleteRequestDto.getUserName())
                .orElseThrow(() -> new NotExistUserException("Not exist username: " + memberDeleteRequestDto.getUserName()));
        memberRepository.delete(member);
    }


}
