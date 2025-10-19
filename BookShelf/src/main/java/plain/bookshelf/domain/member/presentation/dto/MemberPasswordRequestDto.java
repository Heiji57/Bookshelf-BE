package plain.bookshelf.domain.member.presentation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberPasswordRequestDto {
    private String username;
    private String password;
}
