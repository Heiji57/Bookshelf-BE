package plain.bookshelf.domain.member.exception;


public class NotExistUserException extends RuntimeException {
    public NotExistUserException(String message) {
        super("Not exist user: " + message);
    }
}
