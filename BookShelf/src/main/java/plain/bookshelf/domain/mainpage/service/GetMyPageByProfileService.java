package plain.bookshelf.domain.mainpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;

@Service
@RequiredArgsConstructor
public class GetMyPageByProfileService {

    private final GetCurrentMemberService getCurrentMemberService;

    public Member getCurrentMember() { return getCurrentMemberService.getCurrentMember(); }
}
