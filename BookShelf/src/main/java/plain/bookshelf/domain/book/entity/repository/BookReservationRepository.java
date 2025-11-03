package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookReservation;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.member.entity.Member;

import java.util.List;

@Repository
public interface BookReservationRepository extends JpaRepository<BookReservation, MemberBookDetailId> {
    @EntityGraph(attributePaths = {"bookDetail", "bookDetail.book"})
    List<BookReservation> findBookReservationByMember(Member member);

    List<BookReservation> findByBookDetailOrderByReservationRankAsc(BookDetail bookDetail);

    BookReservation findByBookDetailAndMember(BookDetail bookDetail, Member member);

    @Query("SELECT MAX(r.reservationRank) FROM BookReservation r WHERE r.bookDetail = :bookDetail")
    Integer findBookReservationMaxRankByBookDetail(@Param("bookDetail") BookDetail bookDetail);

    @Modifying
    @Query("UPDATE BookReservation br SET br.reservationRank = br.reservationRank - 1 " + //벌크 업서트 사용하는 법
            "WHERE br.bookDetail = :bookDetail AND br.reservationRank > 1")
    void decreaseBookReservationRanks(@Param("bookDetail") BookDetail bookDetail);
}
