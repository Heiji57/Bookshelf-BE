package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookReaction;

@Repository
public interface BookReactionRepository extends JpaRepository<BookReaction, Integer> {

}
