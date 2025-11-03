package plain.bookshelf.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class PostProfileService {
    private final MemberRepository memberRepository;

    public void postProfile(Long userId, String profile) {
        Member member = memberRepository.findMemberById(userId);

        member.setProfile(profile);

        memberRepository.save(member);
    }
}
