package plain.bookshelf.domain.book.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class AnyMoreRentalException extends BaseCustomException {

    public AnyMoreRentalException() {
        super(ErrorCode.ANY_MORE_RENTAL);
    }
}
