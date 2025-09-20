package plain.bookshelf.domain.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
public class MemberSignupRequestDto {

    private String userName;

    private String nickName;

    @NotBlank
    private String password;

    private List<String> emails; // 선택

    public UsernamePasswordAuthenticationToken toAutentication() {
        return new UsernamePasswordAuthenticationToken(this.userName, this.password);
    }
}
