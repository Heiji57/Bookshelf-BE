package plain.bookshelf.domain.email.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.email.service.EmailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody Email email) {
        Email sentEmail = emailService.sendVerificationEmail(email);
        return "Email sent: " + sentEmail.isVerified();
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestBody String verificationCode) {
        boolean result = emailService.verifyEmail(verificationCode);
        return result ? "Email verified" : "Invalid verification code.";
    }
}
