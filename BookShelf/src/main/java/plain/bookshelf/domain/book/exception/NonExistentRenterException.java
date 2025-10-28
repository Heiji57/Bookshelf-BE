package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NonExistentRenterException extends RuntimeException {

    private final ErrorCode errorCode;

    public NonExistentRenterException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
