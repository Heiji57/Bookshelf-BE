package plain.bookshelf.domain.managerpage.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotFoundBookRentalRecordException extends BaseCustomException {

    public  NotFoundBookRentalRecordException() {
        super(ErrorCode.NOT_FOUND_BOOK_RENTAL_RECORD);
    }
}
