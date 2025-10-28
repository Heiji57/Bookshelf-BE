package plain.bookshelf.domain.member.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberPasswordRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) { }
