package plain.bookshelf.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.email.entity.Email;
import plain.bookshelf.email.entity.repository.EmailRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final MailService mailService;

    public Email sendVerificationEmail(Email email) {

        // 인증 코드 생성
        String verificationCode = UUID.randomUUID().toString();
        email.setVerificationCode(verificationCode);

        // 이메일 전송
        boolean sent = mailService.sendEmail(
                email.getAddress(),
                "이메일 인증 코드",
                "인증 코드: " + verificationCode

        );

        email.setDelivered(sent);
        return emailRepository.save(email);
    }

    public boolean verifyEmail(String verificationCode) {
        Optional<Email> OptionalEmail = emailRepository.findByVerificationCode(verificationCode);
        if (OptionalEmail.isPresent()) {
            Email email = OptionalEmail.get();
            email.setVerified(true);
            emailRepository.save(email);
            return true;
        }
        return false;
    }
}
