package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plain.bookshelf.domain.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
