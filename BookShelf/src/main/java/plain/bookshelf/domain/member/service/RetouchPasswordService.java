package plain.bookshelf.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class RetouchPasswordService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED)
    public void retouchPassword(String username, String password) {
        Member member = memberRepository.findByUserName(username)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));

        member.retouchPassword(passwordEncoder.encode(password));
    }
}
