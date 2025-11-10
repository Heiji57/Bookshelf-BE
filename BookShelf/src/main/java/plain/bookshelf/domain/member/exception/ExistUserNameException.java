package plain.bookshelf.domain.member.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class ExistUserNameException extends BaseCustomException {

    public ExistUserNameException() {
        super(ErrorCode.MEMBER_ID_EXIST);
    }
}
