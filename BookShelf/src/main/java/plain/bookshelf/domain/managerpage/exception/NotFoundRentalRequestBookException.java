package plain.bookshelf.domain.managerpage.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotFoundRentalRequestBookException extends BaseCustomException {

    public NotFoundRentalRequestBookException() {
        super(ErrorCode.NOT_FOUND_RENTAL_REQUEST_BOOK);
    }
}
