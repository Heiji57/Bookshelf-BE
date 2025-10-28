package plain.bookshelf.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.email.presentation.dto.request.VerifyEmailRequestDto;

@Service
@RequiredArgsConstructor
public class VerifyUsernameService {

    private final RedisTemplate<String, String> redisTemplate;
    private final EmailRepository emailRepository;

    public boolean verifyUsername(VerifyEmailRequestDto verifyEmailRequestDto) {
        String address =  verifyEmailRequestDto.address();
        String code = redisTemplate.opsForValue().get("verification:member:" + address);

        if (code.equals(verifyEmailRequestDto.verificationCode()) && emailRepository.findEmailByAddress(address).isPresent()) {
            redisTemplate.delete("verification:member:" + address);
            return true;
        }
        return false;
    }
}
