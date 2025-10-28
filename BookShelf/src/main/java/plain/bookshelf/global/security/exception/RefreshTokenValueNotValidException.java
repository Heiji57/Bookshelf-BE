package plain.bookshelf.global.security.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class RefreshTokenValueNotValidException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String details;

    public RefreshTokenValueNotValidException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = details;
    }
}
