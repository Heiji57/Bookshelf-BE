package plain.bookshelf.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.email.exception.ExistEmailException;
import plain.bookshelf.domain.email.presentation.dto.request.GetEmailRequestDto;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SendVerificationCodeService {

    private final EmailRepository emailRepository;
    private final MailService mailService;
    private final RedisTemplate<String, String> redisTemplate;
    private static final int VERIFICATION_TTL_MINUTES = 5;

    public void sendVerificationEmail(GetEmailRequestDto getEmailRequestDto) {

        if (emailRepository.findEmailByAddress(getEmailRequestDto.address()).isPresent()) {
            throw new ExistEmailException();
        }

        // 인증 코드 생성
        String verificationCode = RandomStringUtils.randomAlphanumeric(6);
        
        String key = "verification:email:" + getEmailRequestDto.address();
        redisTemplate.opsForValue().set(key, verificationCode, Duration.ofMinutes(VERIFICATION_TTL_MINUTES));

        // 이메일 전송
        mailService.sendEmail(
                getEmailRequestDto.address(),
                "이메일 인증 코드",
                "인증 코드: " + verificationCode
        );
    }
}
