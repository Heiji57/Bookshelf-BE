package plain.bookshelf.domain.member.presentation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRequestDto {
    private String refreshToken;
    private String accessToken;
}
