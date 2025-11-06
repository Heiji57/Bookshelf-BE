package plain.bookshelf.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import plain.bookshelf.domain.book.exception.*;
import plain.bookshelf.domain.email.exception.ExistEmailException;
import plain.bookshelf.domain.email.exception.NotCorrectVerificationCodeException;
import plain.bookshelf.domain.email.exception.NotExistEmailException;
import plain.bookshelf.domain.managerpage.exception.NotFoundBookRentalRecordException;
import plain.bookshelf.domain.managerpage.exception.NotFoundRentalRequestBookException;
import plain.bookshelf.domain.member.exception.*;
import plain.bookshelf.global.security.exception.AccessTokenValueNotValidException;
import plain.bookshelf.global.security.exception.RefreshTokenValueNotValidException;

@RestControllerAdvice
@Slf4j
@Aspect
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyAssignedEmailException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(AlreadyAssignedEmailException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] AlreadyAssignedEmailException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(ExistNickNameException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(ExistNickNameException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] ExistNickNameException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(ExistUserNameException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(ExistUserNameException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] ExistUserNameException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(NotExistUserException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(NotExistUserException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] NotExistUserException - URI: {}. Code: {}, Message: {}}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(ExistEmailException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(ExistEmailException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] ExistEmailException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(NotCorrectVerificationCodeException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(NotCorrectVerificationCodeException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] NotCorrectVerificationCodeException - URI: {}. Code: {}, Message: {}}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(RefreshTokenValueNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(RefreshTokenValueNotValidException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] RefreshTokenValueNotValidException - URI: {}. Code: {}, Message: {}, Details: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage(), e.getDetails());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode(), e.getDetails());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(AccessTokenValueNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(AccessTokenValueNotValidException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] AccessTokenValueNotValidException - URI: {}. Code: {}, Message: {}, Details: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage(), e.getDetails());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode(), e.getDetails());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(NotExistEmailException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(NotExistEmailException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] NotExistEmailException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(BookNotFoundException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] BookNotFoundException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(NonExistentRenterException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(NonExistentRenterException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] NonExistentRenterException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(NonExistentReservationPeopleException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(NonExistentReservationPeopleException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] NonExistentReservationPeopleException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(NotExistBookCommentException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(NotExistBookCommentException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] NotExistBookCommentException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(RetouchBookCommentException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(RetouchBookCommentException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] RetouchBookCommentException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(AlreadyRentalBookException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(AlreadyRentalBookException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] AlreadyRentalBookException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(AnyMoreRentalException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(AnyMoreRentalException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] AnyMoreRentalException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(MemberOverdueException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(MemberOverdueException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] MemberOverdueException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(AlreadyReservationException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(AlreadyReservationException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] AlreadyReservationException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(AlreadyReservationOrRentalException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(AlreadyReservationOrRentalException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] AlreadyReservationOrRentalException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(NotFoundRentalRequestBookException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(NotFoundRentalRequestBookException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] NotFoundRentalBookException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(NotFoundBookRentalRecordException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(NotFoundBookRentalRecordException e, HttpServletRequest request) {
        log.error("[ExceptionHandler] NotFoundBookRentalRecordException - URI: {}. Code: {}, Message: {}", request.getRequestURI(), e.getErrorCode(), e.getMessage());

        ErrorResponseDto responseDto = ErrorResponseDto.of(e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(responseDto);
    }
}
