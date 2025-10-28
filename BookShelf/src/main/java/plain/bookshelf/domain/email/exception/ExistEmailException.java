package plain.bookshelf.domain.email.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class ExistEmailException extends RuntimeException {

    private final ErrorCode errorCode;

    public ExistEmailException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
