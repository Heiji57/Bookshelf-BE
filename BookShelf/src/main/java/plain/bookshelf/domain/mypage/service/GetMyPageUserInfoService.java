package plain.bookshelf.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.domain.mypage.presentation.dto.response.GetMyPageUserInfoResponseDto;
import plain.bookshelf.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class GetMyPageUserInfoService {
    private final MemberRepository memberRepository;
    private final EmailRepository emailRepository;

    public GetMyPageUserInfoResponseDto getMyPageUserInfo(Long userId) {
        Member member = memberRepository.findById(userId).
                orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));

        Email email = emailRepository.findEmailByMember(member)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.EMAIL_NOT_FOUND));

        return GetMyPageUserInfoResponseDto.of(member.getNickName(), email.getAddress());
    }
}
