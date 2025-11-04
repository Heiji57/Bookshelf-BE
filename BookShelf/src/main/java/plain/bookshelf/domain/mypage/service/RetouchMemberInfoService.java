package plain.bookshelf.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.domain.mypage.presentation.dto.request.RetouchMemberInfoRequestDto;
import plain.bookshelf.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class RetouchMemberInfoService {
    private final MemberRepository memberRepository;

    public void retouchMemberInfo(Long userId, RetouchMemberInfoRequestDto retouchMemberInfoRequestDto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));

        if (retouchMemberInfoRequestDto.nickName() != null) {
            member.setNickName(retouchMemberInfoRequestDto.nickName());
        }
    }
}
