package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class RetouchBookCommentException extends RuntimeException {

    private final ErrorCode errorCode;

    public RetouchBookCommentException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
