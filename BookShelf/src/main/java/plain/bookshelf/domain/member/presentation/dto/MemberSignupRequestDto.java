package plain.bookshelf.domain.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSignupRequestDto {

    private String username;

    private String nickName;

    @NotBlank
    private String password;

    private String address; // 선택
}
