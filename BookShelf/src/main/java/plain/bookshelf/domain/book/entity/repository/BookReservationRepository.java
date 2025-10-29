package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookReservation;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookReservationRepository extends JpaRepository<BookReservation, MemberBookDetailId> {
    @EntityGraph(attributePaths = {"BookDetail", "BookDetail.Book"})
    List<BookReservation> findBookReservationByMember(Member member);

    List<BookReservation> findBookReservationByBookDetail(BookDetail bookDetail);

    @Query("SELECT MAX(r.reservationRank) FROM BookReservation r WHERE r.bookDetail = :bookDetail")
    Integer findBookReservationMaxRankByBookDetail(@Param("bookDetail") BookDetail bookDetail);

    Optional<BookReservation> findTopByBookDetailOrderByReservationRankAsc(BookDetail bookDetail);

    @Query("SELECT br FROM BookReservation br " +
            "WHERE br.reservationRank = 1 AND br.bookDetail = :bookDetail")
    @EntityGraph(attributePaths = "Member")
    Optional<BookReservation> findBookDetailByBookReservationRankAndBookDetail(@Param("bookDetail") BookDetail bookDetail);

    BookReservation findBookDetailByMemberAndBookDetail(Member member, BookDetail bookDetail);
}
