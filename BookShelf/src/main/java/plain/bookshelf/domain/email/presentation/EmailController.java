package plain.bookshelf.domain.email.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.email.exception.NotCorrectVerificationCodeException;
import plain.bookshelf.domain.email.presentation.dto.GetEmailRequestDto;
import plain.bookshelf.domain.email.presentation.dto.VerifyEmailRequestDto;
import plain.bookshelf.domain.email.service.EmailService;
import plain.bookshelf.global.StatusResponseDto;
import plain.bookshelf.global.exception.ErrorCode;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody @Valid GetEmailRequestDto getEmailRequestDto) {
        emailService.sendVerificationEmail(getEmailRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK,"successfully send email.", ""));
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody @Valid VerifyEmailRequestDto verifyEmailRequestDto) {
        boolean result = emailService.verifyEmail(verifyEmailRequestDto.getVerificationCode(), verifyEmailRequestDto.getAddress());
        log.info("Email verified: " + result);
        if (!result) {
            throw new NotCorrectVerificationCodeException(ErrorCode.EMAIL_VERIFICATION_CODE_NOT_CORRECT);
        }
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK,"successfully verified.", ""));
    }
}
