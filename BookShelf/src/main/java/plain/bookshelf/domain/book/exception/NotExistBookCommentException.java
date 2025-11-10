package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotExistBookCommentException extends BaseCustomException {

    public NotExistBookCommentException() {
        super(ErrorCode.BOOK_COMMENT_NOT_FOUND);
    }
}
