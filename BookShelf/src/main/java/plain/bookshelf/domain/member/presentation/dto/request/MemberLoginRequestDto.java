package plain.bookshelf.domain.member.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record MemberLoginRequestDto(
        @NotBlank
        String credential,
        @NotBlank
        String password
) {
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(credential, password);
    }
}
