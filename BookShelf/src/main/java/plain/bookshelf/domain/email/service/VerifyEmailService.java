package plain.bookshelf.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;

@Service
@RequiredArgsConstructor
public class VerifyEmailService {

    private final EmailRepository emailRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public boolean verifyEmail(String verificationCode, String address) {

        String code = redisTemplate.opsForValue().get("verification:email:" + address);
        if (code.equals(verificationCode)) {
            Email email = Email.builder()
                    .address(address)
                    .verified(true)
                    .build();
            emailRepository.save(email);
            redisTemplate.delete("verification:email:" + address);
            return true;
        }
        return false;
    }
}
