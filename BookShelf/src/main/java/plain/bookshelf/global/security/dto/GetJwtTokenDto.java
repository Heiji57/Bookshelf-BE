package plain.bookshelf.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetJwtTokenDto {

    private String jwtAccessToken;

    private String jwtRefreshToken;
}
