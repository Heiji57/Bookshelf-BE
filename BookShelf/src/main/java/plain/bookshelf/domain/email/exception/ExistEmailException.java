package plain.bookshelf.domain.email.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class ExistEmailException extends BaseCustomException {

    public ExistEmailException() {
        super(ErrorCode.MEMBER_EMAIL_EXIST);
    }
}
