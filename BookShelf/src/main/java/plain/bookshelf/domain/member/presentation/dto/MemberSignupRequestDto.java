package plain.bookshelf.domain.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
public class MemberSignupRequestDto {

    @NotBlank
    private String userName;

    @NotBlank
    private String nickName;

    @NotBlank
    private String password;

    @NotEmpty
    private List<String> emails;

    private boolean emailVerified;

    private boolean emailDelivered;
}
