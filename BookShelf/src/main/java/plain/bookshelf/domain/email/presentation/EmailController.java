package plain.bookshelf.domain.email.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.email.presentation.dto.GetEmailRequestDto;
import plain.bookshelf.domain.email.presentation.dto.VerifyEmailRequestDto;
import plain.bookshelf.domain.email.service.EmailService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid GetEmailRequestDto getEmailRequestDto) {
        emailService.sendVerificationEmail(getEmailRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .build();
    }

    @PutMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@RequestBody @Valid VerifyEmailRequestDto verifyEmailRequestDto) {
        boolean result = emailService.verifyEmail(verifyEmailRequestDto.getVerificationCode(), verifyEmailRequestDto.getAddress());
        log.info("Email verified: " + result);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .build();
    }
}
