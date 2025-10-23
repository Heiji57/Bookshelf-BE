package plain.bookshelf.domain.member.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberSignupRequestDto(
        @NotBlank
        String username,
        String nickname, // 선택
        @NotBlank
        String password,
        @NotBlank
        String affiliationName,
        String address // 선택
) { }
