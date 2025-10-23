package plain.bookshelf.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.email.exception.NotCorrectVerificationCodeException;
import plain.bookshelf.domain.email.exception.NotExistEmailException;
import plain.bookshelf.domain.email.presentation.dto.request.VerifyEmailRequestDto;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class FindUsernameService {

    private final MemberRepository memberRepository;
    private final EmailRepository emailRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public String findUsername(VerifyEmailRequestDto verifyEmailRequestDto) {
        Email email = emailRepository.findEmailByAddress(verifyEmailRequestDto.address())
                .orElseThrow(() -> new NotExistEmailException(ErrorCode.EMAIL_NOT_FOUND));

        Member member = memberRepository.findAllByEmails(email)
                        .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));

        String address =  verifyEmailRequestDto.address();
        String code = redisTemplate.opsForValue().get("verification:member:" + address);

        if (code.equals(verifyEmailRequestDto.verificationCode()) && emailRepository.findEmailByAddress(address).isPresent()) {
            redisTemplate.delete("verification:member:" + address);
            return member.getUserName();
        }

        throw new NotCorrectVerificationCodeException(ErrorCode.EMAIL_VERIFICATION_CODE_NOT_CORRECT);
    }
}
