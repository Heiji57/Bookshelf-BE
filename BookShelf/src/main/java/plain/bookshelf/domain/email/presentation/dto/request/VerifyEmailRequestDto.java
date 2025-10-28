package plain.bookshelf.domain.email.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record VerifyEmailRequestDto(
        @NotBlank
        String address,
        @NotBlank
        String verificationCode
) { }
