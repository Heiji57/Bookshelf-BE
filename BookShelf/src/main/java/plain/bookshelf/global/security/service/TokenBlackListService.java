package plain.bookshelf.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlackListService {

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;
    private final String REDIS_BLACK_LIST_KEY = "tokenBlackList";

    public void blacklistToken(String accessToken, long expirationTimeMillis) {
        if (expirationTimeMillis <= 0) {
            return;
        }

        String key = REDIS_BLACK_LIST_KEY + accessToken;

        redisTemplate.opsForValue().set(
                key,
                "logged_out",
                expirationTimeMillis,
                TimeUnit.MILLISECONDS
        );
    }

    public boolean isBlacklisted(String accessToken) {
        String key = REDIS_BLACK_LIST_KEY + accessToken;

        return redisTemplate.hasKey(key); // redis에 해당 Key가 존재하는지 확인
    }
}
