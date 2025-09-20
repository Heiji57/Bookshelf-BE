package plain.bookshelf.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRefreshTokenDto {
    private String jwtRefreshToken;
}
