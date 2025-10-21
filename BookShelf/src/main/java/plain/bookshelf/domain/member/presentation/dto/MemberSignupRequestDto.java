package plain.bookshelf.domain.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSignupRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String nickName;

    @NotBlank
    private String password;

    @NotBlank
    private String affiliationName;

    private String address; // 선택
}
