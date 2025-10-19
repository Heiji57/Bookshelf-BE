package plain.bookshelf.domain.email.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyEmailRequestDto {
    @NotBlank
    private String address;
    @NotBlank
    private String verificationCode;
}
