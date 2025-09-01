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

    private List<String> emails; // 선택

    private boolean emailDelivered; // 수신 상태
}
