package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.member.entity.Member;

@Service
@RequiredArgsConstructor
public class GetCurrentMemberService {

    public Member getCurrentMemberByBookDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        return member;
    }
}
