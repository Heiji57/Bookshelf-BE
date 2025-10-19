package plain.bookshelf.domain.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginRequestDto {
    @NotBlank
    private String credential;
    @NotBlank
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return  new UsernamePasswordAuthenticationToken(credential, password);
    }
}
