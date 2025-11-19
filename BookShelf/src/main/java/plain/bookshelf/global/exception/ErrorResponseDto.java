package plain.bookshelf.global.exception;

import lombok.Builder;

@Builder
public record ErrorResponseDto(String code, int status, String message, String details) {

    // ErrorCode를 인자로 받아 ErrorResponse를 생성하는 팩토리 메서드
    public static ErrorResponseDto of(ErrorCode errorCode) {
        return ErrorResponseDto.builder()
                .code(errorCode.getCode())
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .details(null)
                .build();
    }

    public static ErrorResponseDto of(ErrorCode errorCode, String details) {
        return ErrorResponseDto.builder()
                .code(errorCode.getCode())
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .details(details)
                .build();
    }
}
