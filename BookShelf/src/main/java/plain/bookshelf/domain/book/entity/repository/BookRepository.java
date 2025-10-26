package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByBookId(Long bookId);
}
