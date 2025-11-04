package plain.bookshelf.domain.email.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.email.exception.NotCorrectVerificationCodeException;
import plain.bookshelf.domain.email.presentation.dto.request.GetEmailRequestDto;
import plain.bookshelf.domain.email.presentation.dto.request.VerifyEmailRequestDto;
import plain.bookshelf.domain.email.service.SendVerificationCodeService;
import plain.bookshelf.domain.email.service.VerifyEmailService;
import plain.bookshelf.global.dto.StatusResponseDto;
import plain.bookshelf.global.exception.ErrorCode;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/email")
public class EmailController {

    private final VerifyEmailService verifyEmailService;
    private final SendVerificationCodeService sendVerificationEmailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody @Valid GetEmailRequestDto getEmailRequestDto) {
        sendVerificationEmailService.sendVerificationEmail(getEmailRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK,"successfully send email.", ""));
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody @Valid VerifyEmailRequestDto verifyEmailRequestDto) {
        boolean result = verifyEmailService.verifyEmail(verifyEmailRequestDto.verificationCode(), verifyEmailRequestDto.address());

        log.info("Email verified: " + result);

        if (!result) {
            throw new NotCorrectVerificationCodeException(ErrorCode.EMAIL_VERIFICATION_CODE_NOT_CORRECT);
        }

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK,"successfully verified.", result));
    }
}
