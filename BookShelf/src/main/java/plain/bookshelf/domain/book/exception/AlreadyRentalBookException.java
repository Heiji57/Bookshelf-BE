package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class AlreadyRentalBookException extends BaseCustomException {

    public AlreadyRentalBookException() {
        super(ErrorCode.ALREADY_RENTAL_BOOK);
    }
}
