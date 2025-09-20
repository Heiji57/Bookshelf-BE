package plain.bookshelf.global.security.exception;

import plain.bookshelf.global.exception.ErrorCode;

public class UnauthorizedException extends RuntimeException {

  private final ErrorCode errorCode;

  public UnauthorizedException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return this.errorCode;
  }

}
