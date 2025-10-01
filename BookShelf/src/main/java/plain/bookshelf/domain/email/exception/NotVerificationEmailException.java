package plain.bookshelf.domain.email.exception;

public class NotVerificationEmailException extends RuntimeException {
    public NotVerificationEmailException(String address) {
        super("Not verified email: " + address);
    }
}
