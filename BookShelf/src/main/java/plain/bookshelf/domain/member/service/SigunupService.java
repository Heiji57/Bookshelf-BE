package plain.bookshelf.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.email.exception.NotVerificationEmailException;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.UserMemberRepository;
import plain.bookshelf.domain.member.exception.AlreadyAssignedEmailException;
import plain.bookshelf.domain.member.exception.ExistNickNameException;
import plain.bookshelf.domain.member.exception.ExistUserNameException;
import plain.bookshelf.domain.member.presentation.dto.MemberSignupRequestDto;
import plain.bookshelf.domain.member.presentation.dto.MemberSignupResponseDto;
import plain.bookshelf.global.exception.ErrorCode;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SigunupService {
    private final UserMemberRepository userMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;

    @Transactional
    public MemberSignupResponseDto signup(MemberSignupRequestDto memberSignupRequestDto) {

        String address = memberSignupRequestDto.getAddress();
        Optional<Email> email;
        if (address != null) {
            email = emailRepository.findEmailByAddress(memberSignupRequestDto.getAddress());
            // 3. 이미 등록된 이메일 체크
            if (email.isPresent() && email.get().getMember() != null) {
                throw new AlreadyAssignedEmailException(ErrorCode.MEMBER_EMAIL_ALREADY_USED);
            }
            // 4. 인증이 완료된 이메일인지 체크
            if (email.isPresent() && !email.get().isVerified()) {
                throw new NotVerificationEmailException(ErrorCode.EMAIL_VERIFICATION_CODE_NOT_CORRECT);
            }
        }

        // 1. 아이디 중복 체크
        if (userMemberRepository.existsByUserName(memberSignupRequestDto.getUserName())) {
            throw new ExistUserNameException(ErrorCode.MEMBER_ID_EXIST);
        }
        // 2. 닉네임 중복 체크
        if (userMemberRepository.existsByNickName(memberSignupRequestDto.getNickName())) {
            throw new ExistNickNameException(ErrorCode.MEMBER_NICKNAME_EXIST);
        }

        // 3. Member 객체 생성
        Member member = Member.builder()
                .userName(memberSignupRequestDto.getUserName())
                .nickName(memberSignupRequestDto.getNickName())
                .password(passwordEncoder.encode(memberSignupRequestDto.getPassword()))
                .authority(Member.Authority.ROLE_USER)
                .build();


        if (address != null) {
            email = emailRepository.findEmailByAddress(memberSignupRequestDto.getAddress());
            if (email.isPresent()) {
                email.get().setMember(member);
                member.getEmails().add(email.get());
                emailRepository.save(email.get());
            }
        }

        // 5. DB 저장
        Member savedMember = userMemberRepository.save(member);

        // 6. Response DTO 반환
        return MemberSignupResponseDto.of(savedMember);
    }
}
