package plain.bookshelf.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /*
    Member ErrorCode
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "회원정보가 없습니다."),
    MEMBER_EMAIL_ALREADY_USED(HttpStatus.CONFLICT, "M002", "이미 사용되는 email 입니다."),
    MEMBER_ID_EXIST(HttpStatus.CONFLICT, "M003", "이미 등록된 아이디 입니다."),
    MEMBER_NICKNAME_EXIST(HttpStatus.CONFLICT, "M004", "이미 등록된 이름 입니다."),
    MEMBER_NOT_VALID_EMAIL(HttpStatus.BAD_REQUEST, "M005", "인증이 되지 않은 email 입니다."),


    /*
    Common Error
     */
    EMAIL_VERIFICATION_CODE_NOT_CORRECT(HttpStatus.BAD_REQUEST, "C001", "이메일 verificationCode 가 일치하지 않습니다."),
    MEMBER_EMAIL_EXIST(HttpStatus.CONFLICT, "C002", "이미 존재하는 email 입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "C003", "email 을 찾지 못 했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C004", "부적절한 요청 데이터입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C005", "허용되지 않은 HTTP 메서드입니다."),

    // --- 최후의 방어선 ---
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C999", "서버 내부에서 알 수 없는 오류가 발생했습니다."),

    /*
    Auth Error
     */
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A001", "만료된 access token 입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A002", "만료된 refresh token 입니다."),
    ACCESS_TOKEN_NOT_MATCH(HttpStatus.FORBIDDEN, "A003","유효하지 않은 access token 입니다."),
    REFRESH_TOKEN_NOT_MATCH(HttpStatus.FORBIDDEN, "A004", "유효하지 않은 refresh token 입니다. 다시 로그인 하세요.");

    /*
    C: Common(공통 에러)
    M: Member(회원 관련 에러)
    A: Auth(인증/권한 관련 에러)
    001: 해당 카테고리 내의 번호
     */

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
