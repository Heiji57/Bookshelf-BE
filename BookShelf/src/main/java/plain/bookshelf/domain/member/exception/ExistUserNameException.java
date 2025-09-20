package plain.bookshelf.domain.member.exception;

public class ExistUserNameException extends RuntimeException {
    public ExistUserNameException(String id) {
        super("이미 존재하는 아이디입니다: " + id);
    }
}
