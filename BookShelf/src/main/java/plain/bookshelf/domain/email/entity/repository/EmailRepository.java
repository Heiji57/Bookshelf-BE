package plain.bookshelf.domain.email.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findEmailByAddress(String address);
    Optional<Email> findEmailByMember(Member member);
}
