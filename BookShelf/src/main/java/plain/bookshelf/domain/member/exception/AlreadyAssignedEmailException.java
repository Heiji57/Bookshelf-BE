package plain.bookshelf.domain.member.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class AlreadyAssignedEmailException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyAssignedEmailException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
