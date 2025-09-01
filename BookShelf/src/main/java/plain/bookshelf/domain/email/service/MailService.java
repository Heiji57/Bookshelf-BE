package plain.bookshelf.domain.email.service;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final JavaMailSender javaMailSender;

    public boolean sendEmail(String to, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(mimeMessage);

            log.info("Successfully sent email to " + to);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Failed to send email to " + to);
            return false;
        }
    }
}
