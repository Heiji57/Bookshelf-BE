package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NonExistentReservationPeopleException extends RuntimeException {

    private final ErrorCode errorCode;

    public NonExistentReservationPeopleException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
