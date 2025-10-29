package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.affiliation.entity.Affiliation;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
    @EntityGraph(attributePaths = {"Book"})
    List<BookDetail> findBookDetailByMember(Member member);

    @EntityGraph(attributePaths = {"Book, Member"})
    List<BookDetail> findByRentalRequestStatusTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"Book, Member"})
    List<BookDetail> findByRentalStatusTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"Book"})
    BookDetail findBookDetailByRegistrationNumber(String registrationNumber);

    List<BookDetail> findByBookIdAndAffiliation(Long bookId, Affiliation affiliation);
    List<BookDetail> findByRentalStatusTrueAndReturnDateBefore(LocalDateTime returnDate);
    List<BookDetail> findByOverDueStatusTrue(Pageable pageable);

    @Query("SELECT m FROM Member m " +
            "WHERE m.nickName = :nickname")
    List<BookDetail> findByMemberNickName(@Param("nickname")String nickName);
}
