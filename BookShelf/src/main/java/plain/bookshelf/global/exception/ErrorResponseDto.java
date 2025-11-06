package plain.bookshelf.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.aspectj.lang.annotation.Aspect;

@Builder
@Aspect
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
