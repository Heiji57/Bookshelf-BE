package plain.bookshelf.domain.email.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record GetEmailRequestDto(
        @NotBlank(message = "주소는 필수 입력 정보입니다.")
        String address
) { }
