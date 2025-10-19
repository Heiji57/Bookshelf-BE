package plain.bookshelf.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.email.exception.ExistEmailException;
import plain.bookshelf.domain.email.presentation.dto.GetEmailRequestDto;
import plain.bookshelf.global.exception.ErrorCode;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SendVerificationCodeService {

    private final EmailRepository emailRepository;
    private final MailService mailService;
    private final RedisTemplate<String, String> redisTemplate;
    private static final int VERIFICATION_TTL_MINUTES = 5;

    public void sendVerificationEmail(GetEmailRequestDto getEmailRequestDto) {

        if (emailRepository.findEmailByAddress(getEmailRequestDto.getAddress()).isPresent()) {
            throw new ExistEmailException(ErrorCode.MEMBER_EMAIL_EXIST);
        }

        // 인증 코드 생성
        String verificationCode = RandomStringUtils.randomAlphanumeric(6);
        
        String key = "verification:email:" + getEmailRequestDto.getAddress();
        redisTemplate.opsForValue().set(key, verificationCode, Duration.ofMinutes(VERIFICATION_TTL_MINUTES));

        // 이메일 전송
        mailService.sendEmail(
                getEmailRequestDto.getAddress(),
                "이메일 인증 코드",
                "인증 코드: " + verificationCode
        );
    }
}
