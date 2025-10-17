package plain.bookshelf.domain.member.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class ExistUserNameException extends RuntimeException {

    private final ErrorCode errorCode;

    public ExistUserNameException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
