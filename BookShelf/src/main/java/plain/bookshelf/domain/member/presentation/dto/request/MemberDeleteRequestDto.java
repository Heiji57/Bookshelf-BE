package plain.bookshelf.domain.member.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberDeleteRequestDto(
        @NotBlank
        String username
) { }
