package plain.bookshelf.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class PostProfileService {
    private final MemberRepository memberRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void postProfile(Long userId, String profile) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));

        member.setProfile(profile);

        memberRepository.save(member);
    }
}
