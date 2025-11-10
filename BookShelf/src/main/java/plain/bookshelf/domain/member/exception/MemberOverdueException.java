package plain.bookshelf.domain.member.exception;

import lombok.Getter;
import plain.bookshelf.global.exception.BaseCustomException;
import plain.bookshelf.global.exception.ErrorCode;

@Getter
public class MemberOverdueException extends BaseCustomException {

    public MemberOverdueException() {
        super(ErrorCode.MEMBER_OVERDUE_STATUS);
    }
}
