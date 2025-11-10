package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class AlreadyReservationException extends BaseCustomException {

    public AlreadyReservationException() {
        super(ErrorCode.ALREADY_RESERVATION_BOOK);
    }
}
