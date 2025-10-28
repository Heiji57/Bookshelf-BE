package plain.bookshelf.domain.email.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotCorrectVerificationCodeException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotCorrectVerificationCodeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
