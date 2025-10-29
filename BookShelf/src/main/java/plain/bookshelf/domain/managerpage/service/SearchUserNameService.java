package plain.bookshelf.domain.managerpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchUserNameService {
    private final MemberRepository memberRepository;

    public Member getMemberByNickName(String nickName) {

        return memberRepository.findByNickName(nickName)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
