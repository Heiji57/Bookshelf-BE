package plain.bookshelf.domain.email.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotVerificationEmailException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotVerificationEmailException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
