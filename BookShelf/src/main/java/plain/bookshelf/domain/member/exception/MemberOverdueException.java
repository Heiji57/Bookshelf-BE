package plain.bookshelf.domain.member.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class MemberOverdueException extends RuntimeException {

    private final ErrorCode errorCode;

    public MemberOverdueException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
