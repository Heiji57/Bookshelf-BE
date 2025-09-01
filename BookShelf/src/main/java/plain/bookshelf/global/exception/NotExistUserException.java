package plain.bookshelf.global.exception;


public class NotExistUserException extends RuntimeException {
    public NotExistUserException(String message) {
        super("Not exist user: " + message);
    }
}
