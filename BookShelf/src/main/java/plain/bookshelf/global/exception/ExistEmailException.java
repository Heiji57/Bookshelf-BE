package plain.bookshelf.global.exception;


import java.util.List;

public class ExistEmailException extends RuntimeException {
    public ExistEmailException(List<String> email) {
        super("이미 사용되는 이메일입니다:" + email);
    }
}
