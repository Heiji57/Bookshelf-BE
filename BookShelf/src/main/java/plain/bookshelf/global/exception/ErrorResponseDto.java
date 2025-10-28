package plain.bookshelf.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDto {

    private final String code;
    private final int status;
    private final String message;
    private final String details;

    // ErrorCode를 인자로 받아 ErrorResponse를 생성하는 팩토리 메서드
    public static ErrorResponseDto of(ErrorCode errorCode) {
        return ErrorResponseDto.builder()
                .code(errorCode.getCode())
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .details(null)
                .build();
    }

    // 유효성 검사 실패 시 사용되는 오버로드 메서드
    public static ErrorResponseDto of(ErrorCode errorCode, String details) {
        return ErrorResponseDto.builder()
                .code(errorCode.getCode())
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .details(details)
                .build();
    }
}
