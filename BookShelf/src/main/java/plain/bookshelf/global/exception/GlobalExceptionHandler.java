package plain.bookshelf.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import plain.bookshelf.domain.book.exception.*;
import plain.bookshelf.domain.member.exception.*;

@RestControllerAdvice
@Slf4j
@Aspect
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseCustomException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(BaseCustomException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] Custom Exception - URI: {}. Code: {}, Message: {}, Type: {}", request.getRequestURI(), e.getErrorCode(), e.getErrorCode().getMessage(), e.getClass().getSimpleName());

        ErrorResponseDto responseDto = (e.getDetails() != null)
                ? ErrorResponseDto.of(e.getErrorCode(), e.getDetails())
                : ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

}