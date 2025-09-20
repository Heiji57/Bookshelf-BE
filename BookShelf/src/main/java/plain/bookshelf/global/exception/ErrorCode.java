package plain.bookshelf.global.exception;

public enum ErrorCode {
    // 401 Unauthorized
    INVALID_REFRESH_TOKEN(401, "A001", "Invalid refresh token."),

    // 400 Bad Request
    INVALID_INPUT_VALUE(400, "B001", "Invalid input value."),

    INVALID_TOKEN(400, "B002", "Invalid token"),

    // 404 Not Found
    USER_NOT_FOUND(404, "C001", "User not found."),

    // 기타 등등...
    INTERNAL_SERVER_ERROR(500, "S001", "Server internal error.");

    private final int status;
    private final String code;
    private final String message;

    // 생성자
    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
