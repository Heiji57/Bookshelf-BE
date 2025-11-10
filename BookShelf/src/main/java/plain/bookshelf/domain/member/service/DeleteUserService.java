package plain.bookshelf.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;

@RequiredArgsConstructor
@Service
public class DeleteUserService {
    private final MemberRepository memberRepository;
    private final GetCurrentMemberService getCurrentMemberService;
    private final RedisTemplate<String, String> redisTemplate;
    private final static String REFRESH_TOKEN_PREFIX = "refreshToken:";

    @Transactional(value = Transactional.TxType.REQUIRED)
    public void userDelete () {
        String username = getCurrentMemberService.getCurrentMember().getUserName();
        Member member = memberRepository.findByUserName(username)
                .orElseThrow(NotExistUserException::new);

        redisTemplate.delete(REFRESH_TOKEN_PREFIX + username);
        memberRepository.delete(member);
    }
}
