package plain.bookshelf.domain.mypage.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotMatchExistingPasswordException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotMatchExistingPasswordException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
