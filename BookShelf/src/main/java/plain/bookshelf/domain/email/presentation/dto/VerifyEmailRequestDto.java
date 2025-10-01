package plain.bookshelf.domain.email.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyEmailRequestDto {
    @NotNull
    private String address;
    @NotNull
    private String verificationCode;
}
