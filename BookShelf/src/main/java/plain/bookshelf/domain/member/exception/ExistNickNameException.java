package plain.bookshelf.domain.member.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class ExistNickNameException extends BaseCustomException {

    public ExistNickNameException() {
        super(ErrorCode.MEMBER_ID_EXIST);
    }
}
