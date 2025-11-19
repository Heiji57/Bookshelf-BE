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
    MEMBER_NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "M006", "기존 비밀번호가 일치하지 않습니다."),
    MEMBER_OVERDUE_STATUS(HttpStatus.BAD_REQUEST, "M007" , "연체 상태라 대여 및 예약을 하지 못합니다."),
    MEMBER_ALREADY_RESERVATION_OR_RENTAL(HttpStatus.CONFLICT, "M008", "이미 대여하거나 예약한 책입니다."),

    /*
    Common Error
     */
    // Email Error 000
    EMAIL_VERIFICATION_CODE_NOT_CORRECT(HttpStatus.BAD_REQUEST, "C001", "이메일 verificationCode 가 일치하지 않습니다."),
    MEMBER_EMAIL_EXIST(HttpStatus.CONFLICT, "C002", "이미 존재하는 email 입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "C003", "email 을 찾지 못 했습니다."),

    // Book Error 100
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "C101", "책 정보가 없습니다."),
    NON_EXISTENT_RENTER(HttpStatus.NOT_FOUND, "C102", "대여한 책이 없습니다."),
    NON_EXISTENT_RESERVATION_PEOPLE(HttpStatus.NOT_FOUND, "C103", "예약한 책이 없습니다."),
    BOOK_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C104", "댓글이 존재하지 않습니다."),
    ALREADY_RENTAL_BOOK(HttpStatus.CONFLICT, "C105", "이미 대여된 책입니다."),
    ANY_MORE_RENTAL(HttpStatus.BAD_REQUEST, "C106", "더 이상 책을 대여할 수 없습니다."),
    ALREADY_RESERVATION_BOOK(HttpStatus.CONFLICT, "C107", "이미 예약이 됐습니다."),
    NOT_FOUND_RENTAL_REQUEST_BOOK(HttpStatus.NOT_FOUND, "C108", "대여요청의 책 정보를 찾지 못 했습니다."),
    NOT_FOUND_BOOK_RENTAL_RECORD(HttpStatus.NOT_FOUND, "C109", "대여한 기록이 존재하지 않습니다."),

    // Server Error 400
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C401", "부적절한 요청 데이터입니다."),
    INVALID_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "C403", "토큰에서 값을 추출할 수 없습니다."),
    NOT_FOUND_REQUEST(HttpStatus.NOT_FOUND, "C404", "존재하지 않는 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C405", "허용되지 않은 HTTP 메서드입니다."),

    // --- 최후의 방어선 --- 999
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C999", "서버 내부에서 알 수 없는 오류가 발생했습니다."),

    /*
    Auth Error
     */
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A001", "만료된 access token 입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A002", "만료된 refresh token 입니다."),
    ACCESS_TOKEN_NOT_MATCH(HttpStatus.UNAUTHORIZED, "A003","유효하지 않은 access token 입니다."),
    REFRESH_TOKEN_NOT_MATCH(HttpStatus.UNAUTHORIZED, "A004", "유효하지 않은 refresh token 입니다. 다시 로그인 하세요."),
    NOT_VALID_MEMBER_INFO(HttpStatus.BAD_REQUEST, "A005", "회원정보가 일치하지 않습니다.");

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
