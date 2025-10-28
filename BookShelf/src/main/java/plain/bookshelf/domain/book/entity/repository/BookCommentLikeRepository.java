package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookCommentLike;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookCommentId;

import java.util.Optional;

@Repository
public interface BookCommentLikeRepository extends JpaRepository<BookCommentLike, Long> {
    Optional<BookCommentLike> findByMemberBookCommentId(MemberBookCommentId id);
}
