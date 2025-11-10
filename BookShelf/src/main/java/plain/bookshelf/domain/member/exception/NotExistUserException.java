package plain.bookshelf.domain.member.exception;


import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotExistUserException extends BaseCustomException {

    public NotExistUserException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
