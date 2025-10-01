package plain.bookshelf.domain.email.exception;


import java.util.List;

public class ExistEmailException extends RuntimeException {
    public ExistEmailException(String email) {
        super("이미 사용되는 이메일입니다:" + email);
    }
}
