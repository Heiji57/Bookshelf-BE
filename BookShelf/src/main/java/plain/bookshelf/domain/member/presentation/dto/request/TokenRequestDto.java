package plain.bookshelf.domain.member.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRequestDto(
        @NotBlank
        String refreshToken,
        @NotBlank
        String accessToken
) { }
