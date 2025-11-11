package plain.bookshelf.global.security.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class CustomAuthenticationException extends BaseCustomException {
    public CustomAuthenticationException(String details) {
        super(ErrorCode.INVALID_TOKEN_VALUE, details);
    }
}
