package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookById(Long bookId);

    @Query("SELECT b AS totalCount FROM Book b LEFT JOIN BookDetail bd ON b.id = bd.book.id " +
            "GROUP BY b ORDER BY (b.rentalCount + COALESCE(SUM(bd.reservationCount), 0)) DESC, b.rentalCount DESC ")
    List<Book> findAllOrderByCombinedCountsDesc(Pageable pageable);

    @Query("SELECT b FROM Book b ORDER BY b.publicationDate DESC NULLS LAST")
    List<Book> findAllOrderByPublicationDateDesc(Pageable pageable);
}
