package plain.bookshelf.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.domain.mypage.exception.NotMatchExistingPasswordException;
import plain.bookshelf.domain.mypage.presentation.dto.request.RetouchMemberInfoRequestDto;
import plain.bookshelf.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class RetouchMemberInfoService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void retouchMemberInfo(Long userId, RetouchMemberInfoRequestDto retouchMemberInfoRequestDto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));

        String newPassword = passwordEncoder.encode(retouchMemberInfoRequestDto.changingPassword());

        if (!passwordEncoder.matches(member.getPassword(), newPassword)) {
            throw new NotMatchExistingPasswordException(ErrorCode.MEMBER_NOT_MATCH_PASSWORD);
        }

        if (retouchMemberInfoRequestDto.nickname() != null) {
            member.setNickName(retouchMemberInfoRequestDto.nickname());
        }

        member.setPassword(newPassword);
    }
}
