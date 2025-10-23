package plain.bookshelf.global.security.userdetails;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.email.exception.NotExistEmailException;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final EmailRepository emailRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        if (username.contains("@")) {
            //이메일 형식인지 확인
            return emailRepository.findEmailByAddress(username)
                    .map(email -> new CustomUserDetails(email.getMember()))
                    .orElseThrow(() -> new NotExistEmailException(ErrorCode.EMAIL_NOT_FOUND));
        }
        else {
            // userName으로 회원을 찾음
            return memberRepository.findByUserName(username)
                    .map(CustomUserDetails::new) // Member 객체로 UserDetails 생성
                    .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));
        }
    }

}
