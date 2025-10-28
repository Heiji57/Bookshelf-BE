package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.affiliation.entity.Affiliation;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
    @EntityGraph(attributePaths = {"Book"})
    List<BookDetail> findBookDetailByMember(Member member);
    List<BookDetail> findByBookIdAndAffiliation(Long bookId, Affiliation affiliation);
    List<BookDetail> findByRentalStatusTrueAndReturnDateBefore(LocalDate returnDate);

    @EntityGraph(attributePaths = {"Book"})
    BookDetail findBookDetailByRegistrationNumber(String registrationNumber);
}
