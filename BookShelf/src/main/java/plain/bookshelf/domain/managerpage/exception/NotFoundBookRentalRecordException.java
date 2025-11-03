package plain.bookshelf.domain.managerpage.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotFoundBookRentalRecordException extends RuntimeException {

    private final ErrorCode errorCode;

    public  NotFoundBookRentalRecordException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }
}
