package plain.bookshelf.domain.affiliation.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.affiliation.entity.Affiliation;
import plain.bookshelf.domain.member.entity.Member;

@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    Affiliation findByAffiliationName(String bookAffiliationName);
    Member findMemberByAffiliationName(String bookAffiliationName);
}
