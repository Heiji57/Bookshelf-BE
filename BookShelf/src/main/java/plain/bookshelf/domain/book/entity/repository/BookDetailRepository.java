package plain.bookshelf.domain.book.entity.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
    List<BookDetail> findBookDetailByMember(Member member);

    @EntityGraph(attributePaths = {"book, member"})
    List<BookDetail> findByRentalRequestStatusTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"book, member"})
    List<BookDetail> findByRentalStatusTrue(Pageable pageable);

    BookDetail findByRegistrationNumber(String registrationNumber);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BookDetail> findByRegistrationNumberAndRentalStatusTrue(String registrationNumber);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BookDetail> findBookDetailByRegistrationNumberAndRentalRequestStatusTrue(String registrationNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // 비관적 락
    @Query("SELECT bd FROM BookDetail bd WHERE bd.registrationNumber = :registrationNumber")
    BookDetail findByRegistrationNumberForUpdate(String registrationNumber);

    @Query(value = "SELECT bd FROM BookDetail bd " + "WHERE bd.book.id =:bookId AND bd.affiliation.id =:affiliationId")
    List<BookDetail> findByBookIdAndAffiliationId(@Param("bookId") Long bookId, @Param("affiliationId") Long affiliationId);   // 쿼리 작성 필요
    List<BookDetail> findByRentalStatusTrueAndReturnDateBefore(LocalDateTime returnDate);
    List<BookDetail> findByOverDueStatusTrue(Pageable pageable);

    @Query("SELECT m FROM Member m " +
            "WHERE m.nickName = :nickname")
    List<BookDetail> findByMemberNickName(@Param("nickname")String nickName);
}
