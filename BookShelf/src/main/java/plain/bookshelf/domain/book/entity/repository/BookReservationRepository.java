package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookReservation;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.member.entity.Member;

import java.util.List;

@Repository
public interface BookReservationRepository extends JpaRepository<BookReservation, MemberBookDetailId> {
    @EntityGraph(attributePaths = {"BookDetail", "BookDetail.Book"})
    List<BookReservation> findBookReservationByMember(Member member);
}
