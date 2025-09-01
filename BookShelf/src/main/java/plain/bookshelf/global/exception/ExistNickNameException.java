package plain.bookshelf.global.exception;

public class ExistNickNameException extends RuntimeException {
    public ExistNickNameException(String username) {
        super("이미 존재하는 사용자 이름입니다: " + username);
    }
}
