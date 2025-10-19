package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookDetail;

@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
}
