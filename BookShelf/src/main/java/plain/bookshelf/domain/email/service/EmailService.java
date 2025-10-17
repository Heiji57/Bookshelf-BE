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
public class EmailService {

    private final EmailRepository emailRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final long VERIFICATION_TTL_MINUTES = 5;
    private final MailService mailService;

    public void sendVerificationEmail(GetEmailRequestDto getEmailRequestDto) {

        // 인증 코드 생성
        if (emailRepository.findEmailByAddress(getEmailRequestDto.getAddress()).isPresent()) {
            throw new ExistEmailException(ErrorCode.MEMBER_EMAIL_EXIST);
        }
        String verificationCode = RandomStringUtils.randomAlphanumeric(6);

        Email email = Email.builder()
                .address(getEmailRequestDto.getAddress())
                .delivered(false)
                .verified(false)
                .member(null)
                .build();
        String key = "verification:email:" + email.getAddress();
        redisTemplate.opsForValue().set(key, verificationCode, Duration.ofMinutes(VERIFICATION_TTL_MINUTES));

        // 이메일 전송
        boolean sent = mailService.sendEmail(
                email.getAddress(),
                "이메일 인증 코드",
                "인증 코드: " + verificationCode

        );

        email.setDelivered(sent);
        emailRepository.save(email);
    }

    public boolean verifyEmail(String verificationCode, String address) {
        String code = redisTemplate.opsForValue().get("verification:email:" + address);
        if (code.equals(verificationCode) && emailRepository.findEmailByAddress(address).isPresent()) {
            Email email = emailRepository.findEmailByAddress(address).get();
            email.setVerified(true);
            email.setDelivered(false);
            emailRepository.save(email);
            return true;
        }
        Email email = emailRepository.findEmailByAddress(address).get();
        email.setDelivered(false);
        return false;
    }
}
