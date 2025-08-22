package plain.bookshelf.global.exception;

public class ExistUserException extends RuntimeException {
    public ExistUserException(String username) {
        super("이미 존재하는 사용자 이름입니다: " + username);
    }
}
