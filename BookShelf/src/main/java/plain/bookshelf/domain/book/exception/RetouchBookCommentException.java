package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class RetouchBookCommentException extends BaseCustomException {

    public RetouchBookCommentException() {
        super(ErrorCode.NOT_VALID_MEMBER_INFO);
    }
}
