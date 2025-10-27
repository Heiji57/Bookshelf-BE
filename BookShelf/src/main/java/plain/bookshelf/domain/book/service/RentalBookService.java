package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookRentalRecord;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookRentalRecordRepository;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RentalBookService {
    private final BookDetailRepository bookDetailRepository;
    private final BookRentalRecordRepository bookRentalRecordRepository;

    private final GetCurrentMemberService getCurrentMemberService;

    public void rentalBook(String registrationNumber) {
        BookDetail bookDetail = bookDetailRepository.findBookDetailByRegistrationNumber(registrationNumber);
        Book book = bookDetail.getBook();

        Member member = getCurrentMemberService.getCurrentMemberByBookDetail();

        LocalDateTime now = LocalDateTime.now();

        bookDetail.setRentalStatus(true);
        book.setRentalCount(bookDetail.getBook().getRentalCount() + 1);
        bookDetail.setRenter(member.getId());
        BookRentalRecord bookRentalRecord = BookRentalRecord.builder()
                .bookDetail(bookDetail)
                .member(member)
                .rentalTime(now)
                .returnTime(null)
                .build();

        bookDetailRepository.save(bookDetail);
        bookRentalRecordRepository.save(bookRentalRecord);
    }
}
