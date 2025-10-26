package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookLike;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;

@Repository
public interface BookLikeRepository extends JpaRepository<BookLike, MemberBookDetailId> {
}
