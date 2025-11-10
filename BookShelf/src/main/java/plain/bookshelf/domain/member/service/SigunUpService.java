package plain.bookshelf.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.affiliation.entity.Affiliation;
import plain.bookshelf.domain.affiliation.entity.repository.AffiliationRepository;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.email.exception.NotVerificationEmailException;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.MemberRole;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.AlreadyAssignedEmailException;
import plain.bookshelf.domain.member.exception.ExistNickNameException;
import plain.bookshelf.domain.member.exception.ExistUserNameException;
import plain.bookshelf.domain.member.presentation.dto.request.MemberSignupRequestDto;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SigunUpService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final AffiliationRepository affiliationRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void signup(MemberSignupRequestDto memberSignupRequestDto) {

        String address = memberSignupRequestDto.address();
        Optional<Email> email;
        if (address != null) {
            email = emailRepository.findEmailByAddress(memberSignupRequestDto.address());
            // 3. 이미 등록된 이메일 체크
            if (email.isPresent() && email.get().getMember() != null) {
                throw new AlreadyAssignedEmailException();
            }
            // 4. 인증이 완료된 이메일인지 체크
            if (email.isPresent() && !email.get().isVerified()) {
                throw new NotVerificationEmailException();
            }
        }

        // 1. 아이디 중복 체크
        if (memberRepository.existsByUserName(memberSignupRequestDto.username())) {
            throw new ExistUserNameException();
        }
        // 2. 닉네임 중복 체크
        if (memberRepository.existsByNickName(memberSignupRequestDto.nickname())) {
            throw new ExistNickNameException();
        }

        Affiliation affiliation = affiliationRepository.findByAffiliationName(memberSignupRequestDto.affiliationName());

        // 3. Member 객체 생성
        Member member;
        if (memberSignupRequestDto.nickname() == null) {
            String nickName = memberSignupRequestDto.username();
            member = Member.builder()
                    .userName(memberSignupRequestDto.username())
                    .nickName(nickName)
                    .password(passwordEncoder.encode(memberSignupRequestDto.password()))
                    .authority(MemberRole.ROLE_USER)
                    .affiliation(affiliation)
                    .build();
        } else {
            member = Member.builder()
                    .userName(memberSignupRequestDto.username())
                    .nickName(memberSignupRequestDto.nickname())
                    .password(passwordEncoder.encode(memberSignupRequestDto.password()))
                    .authority(MemberRole.ROLE_USER)
                    .affiliation(affiliation)
                    .build();
        }

        if (address != null) {
            email = emailRepository.findEmailByAddress(memberSignupRequestDto.address());
            if (email.isPresent()) {
                email.get().setMember(member);
                member.getEmails().add(email.get());
                emailRepository.save(email.get());
            }
        }

        // 5. DB 저장
        memberRepository.save(member);
    }
}
