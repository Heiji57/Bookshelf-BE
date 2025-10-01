package plain.bookshelf.domain.member.exception;

public class AlreadyAssignedEmailException extends RuntimeException {
    public AlreadyAssignedEmailException(String errorMessage) {
            super(errorMessage);
    }
}
