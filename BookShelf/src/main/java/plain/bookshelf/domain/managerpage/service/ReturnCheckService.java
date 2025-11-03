package plain.bookshelf.domain.managerpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookRentalRecord;
import plain.bookshelf.domain.book.entity.BookReservation;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookRentalRecordRepository;
import plain.bookshelf.domain.book.entity.repository.BookReservationRepository;
import plain.bookshelf.domain.managerpage.exception.NotFoundBookRentalRecordException;
import plain.bookshelf.domain.managerpage.exception.NotFoundRentalRequestBookException;
import plain.bookshelf.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReturnCheckService {
    private final BookDetailRepository bookDetailRepository;
    private final BookReservationRepository bookReservationRepository;
    private final BookRentalRecordRepository bookRentalRecordRepository;

    private static final LocalDateTime RETURN_DATE = LocalDateTime.now().plusDays(14);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean returnCheck(String registrationNumber) {
        BookDetail bookDetail = bookDetailRepository.findByRegistrationNumberAndRentalStatusTrue(registrationNumber)
                .orElseThrow(() -> new NotFoundRentalRequestBookException(ErrorCode.NOT_FOUND_RENTAL_REQUEST_BOOK));

        BookRentalRecord bookRentalRecord = bookRentalRecordRepository.findByBookDetailAndReturnTimeIsNull(bookDetail)
                .orElseThrow(() -> new NotFoundBookRentalRecordException(ErrorCode.NOT_FOUND_BOOK_RENTAL_RECORD));

        List<BookReservation> bookReservations = bookReservationRepository.findByBookDetailOrderByReservationRankAsc(bookDetail);

        LocalDateTime now = LocalDateTime.now();

        if (bookDetail.isOverDueStatus()) {
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(bookDetail.getReturnDate().toLocalDate(), now.toLocalDate()); // 이렇게 하면 정확한 시간 비교가능
            if (overdueDays > 0) {
                bookDetail.getMember().overduePeriod((int) overdueDays);
            }
        }

        if (!bookReservations.isEmpty()) {
            BookReservation nextRenter = bookReservations.get(0);

            bookDetail.returnBookDate(RETURN_DATE);
            bookDetail.renter(null);
            bookDetail.rentalRequestStatus(true);
            bookDetail.rentalRequestMember(nextRenter.getMember().getNickName());
            bookDetail.requestDate(now);

            bookReservationRepository.delete(nextRenter);

            bookReservationRepository.decreaseBookReservationRanks(bookDetail);
        } else {
            bookDetail.renter(null);
            bookDetail.rentalRequestStatus(false);
            bookDetail.rentalStatus(false);
            bookDetail.resetReservationCount();
        }

        bookRentalRecord.returnTime(now);

        bookRentalRecordRepository.save(bookRentalRecord);
        bookDetailRepository.save(bookDetail);

        return true;
    }
}
