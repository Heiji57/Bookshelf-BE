package plain.bookshelf.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;

@RequiredArgsConstructor
@Service
public class DeleteUserService {
    private final MemberRepository memberRepository;
    private final GetCurrentMemberService getCurrentMemberService;

    @Transactional(value = Transactional.TxType.REQUIRED)
    public void userDelete () {
        String username = getCurrentMemberService.getCurrentMember().getUserName();
        Member member = memberRepository.findByUserName(username)
                .orElseThrow(NotExistUserException::new);

        memberRepository.delete(member);
    }
}
