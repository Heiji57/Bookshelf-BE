package plain.bookshelf.global.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@AllArgsConstructor
@Builder
@RedisHash("refreshToken") // Redis에 저장될 객체임을 명시
public class RefreshToken {

    @Id
    private String key; // 사용자 ID (refresh_key)

    private String value; // refresh token 문자열 (refresh_value)

    @TimeToLive
    private Long expiration; // 만료 시간 (초 단위)

    public RefreshToken updateValue(String value) {
        this.value = value;
        return this;
    }
}
