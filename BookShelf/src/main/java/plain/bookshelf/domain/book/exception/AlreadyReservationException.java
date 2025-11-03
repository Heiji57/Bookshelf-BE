package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class AlreadyReservationException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyReservationException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }
}
