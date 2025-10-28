package plain.bookshelf.domain.email.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class NotExistEmailException extends RuntimeException{

    private ErrorCode errorCode;

    public NotExistEmailException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
