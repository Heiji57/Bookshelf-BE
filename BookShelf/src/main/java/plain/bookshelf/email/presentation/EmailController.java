package plain.bookshelf.email.presentation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.email.entity.Email;
import plain.bookshelf.email.service.EmailService;

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
