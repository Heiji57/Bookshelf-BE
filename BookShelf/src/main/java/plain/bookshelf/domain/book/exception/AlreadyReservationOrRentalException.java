package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class AlreadyReservationOrRentalException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyReservationOrRentalException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }
}
