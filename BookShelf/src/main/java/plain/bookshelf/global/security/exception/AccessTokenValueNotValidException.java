package plain.bookshelf.global.security.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class AccessTokenValueNotValidException extends BaseCustomException {

    public AccessTokenValueNotValidException(String details) {
        super(ErrorCode.ACCESS_TOKEN_NOT_MATCH, details);
    }
}
