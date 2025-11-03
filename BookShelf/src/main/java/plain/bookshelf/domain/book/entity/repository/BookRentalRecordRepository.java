package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookRentalRecord;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface BookRentalRecordRepository extends JpaRepository<BookRentalRecord, MemberBookDetailId> {

    Optional<BookRentalRecord> findByBookDetailAndReturnTimeIsNull(BookDetail bookDetail);
}
