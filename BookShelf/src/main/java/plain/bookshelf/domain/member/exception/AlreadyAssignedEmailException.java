package plain.bookshelf.domain.member.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class AlreadyAssignedEmailException extends BaseCustomException {

    public AlreadyAssignedEmailException() {
        super(ErrorCode.MEMBER_EMAIL_ALREADY_USED);
    }
}
