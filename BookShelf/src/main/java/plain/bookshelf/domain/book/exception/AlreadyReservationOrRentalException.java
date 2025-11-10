package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class AlreadyReservationOrRentalException extends BaseCustomException {

    public AlreadyReservationOrRentalException() {
        super(ErrorCode.MEMBER_ALREADY_RESERVATION_OR_RENTAL);
    }
}
