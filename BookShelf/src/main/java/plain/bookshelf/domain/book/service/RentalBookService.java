package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookRentalRecord;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookRentalRecordRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RentalBookService {
    private final BookDetailRepository bookDetailRepository;
    private final BookRentalRecordRepository bookRentalRecordRepository;

    private final GetCurrentMemberService getCurrentMemberService;

    private static final LocalDateTime RETURN_DATE = LocalDateTime.now().plusDays(14);

    public void rentalBook(String registrationNumber) {
        BookDetail bookDetail = bookDetailRepository.findBookDetailByRegistrationNumber(registrationNumber);
        Book book = bookDetail.getBook();

        Member member = getCurrentMemberService.getCurrentMember();

        LocalDateTime now = LocalDateTime.now();

        bookDetail.setRentalStatus(true);
        bookDetail.setMember(member);
        bookDetail.returnBookDate(RETURN_DATE);
        book.setRentalCount(bookDetail.getBook().getRentalCount() + 1);
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
