package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookReaction;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;

@Repository
public interface BookReactionRepository extends JpaRepository<BookReaction, MemberBookDetailId> {

}
