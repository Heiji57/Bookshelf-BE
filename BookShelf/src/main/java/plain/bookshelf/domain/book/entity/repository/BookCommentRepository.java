package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookComment;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCommentRepository extends JpaRepository<BookComment, MemberBookDetailId> {
    List<BookComment> findBookCommentByBookId(Long bookId);
    Optional<BookComment> findBookCommentsByCommentId(Long commentId);
}
