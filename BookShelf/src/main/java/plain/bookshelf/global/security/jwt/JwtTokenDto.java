package plain.bookshelf.global.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenDto {

    private String grantType;
    private String accessToken;
    private Long expiresIn;
    private String refreshToken;

}
