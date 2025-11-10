package plain.bookshelf.domain.email.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotVerificationEmailException extends BaseCustomException {

    public NotVerificationEmailException() {
        super(ErrorCode.EMAIL_VERIFICATION_CODE_NOT_CORRECT);
    }
}
