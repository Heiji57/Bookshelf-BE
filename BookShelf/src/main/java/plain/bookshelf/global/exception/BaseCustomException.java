package plain.bookshelf.global.exception;

import lombok.Getter;

@Getter
public abstract class BaseCustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private String details;

    public BaseCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseCustomException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = details;
    }
}
