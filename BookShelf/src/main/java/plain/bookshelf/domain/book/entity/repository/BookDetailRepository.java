package plain.bookshelf.domain.book.entity.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
    List<BookDetail> findBookDetailByMember(Member member);

    @EntityGraph(attributePaths = {"book"})
    Page<BookDetail> findByRentalRequestStatusTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"book"})
    Page<BookDetail> findByRentalStatusTrue(Pageable pageable);

    BookDetail findByRegistrationNumber(String registrationNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BookDetail> findByRegistrationNumberAndRentalStatusTrue(String registrationNumber);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BookDetail> findBookDetailByRegistrationNumberAndRentalRequestStatusTrue(String registrationNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // 비관적 락
    @Query("SELECT bd FROM BookDetail bd WHERE bd.registrationNumber = :registrationNumber")
    BookDetail findByRegistrationNumberForUpdate(String registrationNumber);

    @Query(value = "SELECT bd FROM BookDetail bd " + "WHERE bd.book.id =:bookId AND bd.affiliation.id =:affiliationId")
    List<BookDetail> findByBookIdAndAffiliationId(@Param("bookId") Long bookId, @Param("affiliationId") Long affiliationId);

    Page<BookDetail> findByOverDueStatusTrue(Pageable pageable);

    @Query("SELECT m FROM Member m " +
            "WHERE m.nickName = :nickname")
    List<BookDetail> findByMemberNickName(@Param("nickname")String nickName);

    @Query("SELECT b AS totalCount FROM Book b LEFT JOIN BookDetail bd ON b.id = bd.book.id " +
            "GROUP BY b ORDER BY (b.rentalCount + COALESCE(SUM(bd.reservationCount), 0)) DESC, b.rentalCount DESC ")
    List<Book> findAllOrderByCombinedCounts(Pageable pageable);

    @Modifying
    @Query("UPDATE BookDetail bd SET bd.overDueStatus = true WHERE bd.rentalStatus = true AND bd.returnDate < :now")
    void overDueStatus(@Param("now") LocalDateTime now);
}
