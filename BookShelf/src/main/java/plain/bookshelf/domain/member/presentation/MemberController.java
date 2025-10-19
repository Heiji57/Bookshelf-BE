package plain.bookshelf.domain.member.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.email.exception.NotCorrectVerificationCodeException;
import plain.bookshelf.domain.email.presentation.dto.GetEmailRequestDto;
import plain.bookshelf.domain.email.presentation.dto.VerifyEmailRequestDto;
import plain.bookshelf.domain.email.service.FindUsernameSendService;
import plain.bookshelf.domain.member.presentation.dto.*;
import plain.bookshelf.domain.member.service.*;
import plain.bookshelf.global.StatusResponseDto;
import plain.bookshelf.domain.member.service.LogoutService;
import plain.bookshelf.global.exception.ErrorCode;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class MemberController {

    private final SigunupService sigunupService;
    private final DeleteUserService deleteUserService;
    private final LoginService loginService;
    private final ReissueService reissueService;
    private final LogoutService logoutService;
    private final FindUsernameService findUsernameService;
    private final FindUsernameSendService findUsernameSendService;
    private final VerifyUsernameService verifyUsernameService;
    private final RetouchPasswordService retouchPasswordService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberSignupRequestDto memberSignupRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.CREATED,"successfully signup user.",sigunupService.signup(memberSignupRequestDto)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid MemberDeleteRequestDto memberDeleteRequestDto) {
        deleteUserService.userDelete(memberDeleteRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("Content-Type", "application/json")
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK,"successfully delete user.", loginService.login(memberLoginRequestDto)));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        logoutService.logoutService(request);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + request.getHeader("Authorization"))
                .body(StatusResponseDto.of( HttpStatus.OK,"successfully logged out.", ""));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK,"successfully check refresh token valid.", reissueService.reissue(tokenRequestDto)));
    }

    @PostMapping("/find-id/send")
    public ResponseEntity<?> sendFindId(@RequestBody @Valid GetEmailRequestDto getEmailRequestDto) {
        findUsernameSendService.sendFindIdVerificationCode(getEmailRequestDto.getAddress());

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.CREATED,"successfully send.", ""));
    }

    @PostMapping("/find-id/verify")
    public ResponseEntity<?> verifyFindId(@RequestBody @Valid VerifyEmailRequestDto verifyEmailRequestDto) {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK,"successfully verified.", findUsernameService.findUsername(verifyEmailRequestDto)));
    }

    @PostMapping("/find-password/send")
    public ResponseEntity<?> sendFindPassword(@RequestBody @Valid GetEmailRequestDto getEmailRequestDto) {
        findUsernameSendService.sendFindIdVerificationCode(getEmailRequestDto.getAddress());

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully send.", ""));
    }

    @PostMapping("/find-password/verify")
    public ResponseEntity<?> verifyFindPassword(@RequestBody @Valid VerifyEmailRequestDto verifyEmailRequestDto) {
        boolean result = verifyUsernameService.verifyUsername(verifyEmailRequestDto);
        if (!result) {
            throw new NotCorrectVerificationCodeException(ErrorCode.EMAIL_VERIFICATION_CODE_NOT_CORRECT);
        }
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully send.", result));
    }

    @PostMapping("/find-password/retouch")
    public ResponseEntity<?> retouchPassword(@RequestBody @Valid MemberPasswordRequestDto memberPasswordRequestDto) {
        retouchPasswordService.retouchPassword(memberPasswordRequestDto.getUsername(), memberPasswordRequestDto.getPassword());
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully retouch.", ""));
    }
}
