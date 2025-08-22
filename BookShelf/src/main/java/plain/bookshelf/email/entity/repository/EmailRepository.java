package plain.bookshelf.email.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.email.entity.Email;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findByAddress(String address);
    Optional<Email> findByVerificationCode(String verificationCode);
}
