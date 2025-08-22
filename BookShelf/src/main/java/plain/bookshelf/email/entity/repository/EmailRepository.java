package plain.bookshelf.email.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plain.bookshelf.email.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
