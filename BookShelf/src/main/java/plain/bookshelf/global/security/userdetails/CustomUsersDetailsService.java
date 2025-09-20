package plain.bookshelf.global.security.userdetails;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.UserMemberRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService {

    private final UserMemberRepository userMemberRepository;
    private final EmailRepository emailRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.contains("@")) {
            //이메일 형식인지 확인
            return emailRepository.findByAddress(username)
                    .map(email -> new CustomUserDetails(email.getMember()))
                    .orElseThrow(() -> new UsernameNotFoundException(username + ": 아이디를 찾지 못 했습니다."));
        }
        else {
            // userName으로 회원을 찾음
            return userMemberRepository.findByUserName(username)
                    .map(CustomUserDetails::new) // Member 객체로 UserDetails 생성
                    .orElseThrow(() -> new UsernameNotFoundException(username + ": 아이디를 찾지 못 했습니다."));
        }
    }

}
