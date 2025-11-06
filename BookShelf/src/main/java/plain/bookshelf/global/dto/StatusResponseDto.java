package plain.bookshelf.global.dto;

import lombok.*;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Aspect // aop 임을 알림
public class StatusResponseDto<T> {
    private HttpStatus status;
    private String message;
    private T data;

    public static <T> StatusResponseDto<T> of(HttpStatus status, String message, T data) {
        return new StatusResponseDto<>(status, message, data);
    }
}
