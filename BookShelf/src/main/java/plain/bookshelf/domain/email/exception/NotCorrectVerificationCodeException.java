package plain.bookshelf.domain.email.exception;

public class NotCorrectVerificationCodeException extends RuntimeException {
    public NotCorrectVerificationCodeException(String code) {
        super("Not correct verification code: " + code);
    }
}
