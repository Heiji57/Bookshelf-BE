package plain.bookshelf.domain.book_information.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book_information.entity.BookInformation;

@Repository
public interface BookInformationRepository extends JpaRepository<BookInformation, Long> {
}
