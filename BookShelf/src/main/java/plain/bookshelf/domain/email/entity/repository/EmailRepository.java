package plain.bookshelf.domain.email.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.email.entity.Email;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    boolean existsByAddress(String address);
    Optional<Email> findByVerificationCode(String verificationCode);
}
