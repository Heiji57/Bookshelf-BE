package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookLike;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookId;

import java.util.Optional;

@Repository
public interface BookLikeRepository extends JpaRepository<BookLike, MemberBookDetailId> {
    BookLike findByBookId(Long id);

    Optional<BookLike> findById(MemberBookId id);
}
