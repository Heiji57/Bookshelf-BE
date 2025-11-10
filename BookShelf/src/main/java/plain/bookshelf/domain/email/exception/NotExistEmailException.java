package plain.bookshelf.domain.email.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotExistEmailException extends BaseCustomException {

    public NotExistEmailException(){
        super(ErrorCode.EMAIL_NOT_FOUND);
    }
}
