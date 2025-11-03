package plain.bookshelf.domain.managerpage.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotFoundRentalRequestBookException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotFoundRentalRequestBookException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
