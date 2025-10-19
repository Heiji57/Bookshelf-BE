package plain.bookshelf.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.UserMemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.domain.member.presentation.dto.MemberDeleteRequestDto;
import plain.bookshelf.global.exception.ErrorCode;

@RequiredArgsConstructor
@Service
public class DeleteUserService {
    private final UserMemberRepository userMemberRepository;

    @Transactional(value = Transactional.TxType.REQUIRED)
    public void userDelete (MemberDeleteRequestDto memberDeleteRequestDto) {
        Member member = userMemberRepository.findByUserName(memberDeleteRequestDto.getUserName())
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));
        userMemberRepository.delete(member);
    }
}
