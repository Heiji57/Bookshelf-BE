package plain.bookshelf.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.email.exception.NotExistEmailException;
import plain.bookshelf.global.exception.ErrorCode;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class FindSendService {

    private final EmailRepository emailRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final MailService mailService;
    private static final int VERIFICATION_TTL_MINUTES = 5;

    public void sendFindVerificationCode(String address) {

        // 인증 코드 생성
        String verificationCode = RandomStringUtils.randomAlphanumeric(6);

        Email email = emailRepository.findEmailByAddress(address)
                .orElseThrow(() -> new NotExistEmailException(ErrorCode.EMAIL_NOT_FOUND));

        String key = "verification:member:" + email.getAddress();
        redisTemplate.opsForValue().set(key, verificationCode, Duration.ofMinutes(VERIFICATION_TTL_MINUTES));

        // 이메일 전송
        mailService.sendEmail(
                email.getAddress(),
                "이메일 인증 코드",
                "인증 코드: " + verificationCode
        );
    }
}
