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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReturnCheckService {
    private final BookDetailRepository bookDetailRepository;
    private final BookReservationRepository bookReservationRepository;
    private final BookRentalRecordRepository bookRentalRecordRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean returnCheck(String registrationNumber) {
        BookDetail bookDetail = bookDetailRepository.findBookDetailByRegistrationNumber(registrationNumber);
        BookRentalRecord bookRentalRecord = bookRentalRecordRepository.findByBookDetail(bookDetail);

        Optional<BookReservation> bookReservation = bookReservationRepository.findBookDetailByBookReservationRankAndBookDetail(bookDetail);
        List<BookReservation> bookReservations = bookReservationRepository.findBookReservationByBookDetail(bookDetail);

        LocalDateTime now = LocalDateTime.now();

        if (bookDetail.isOverDueStatus()) {
            LocalDateTime overDueDate = now.minusDays(bookDetail.getReturnDate().getDayOfMonth());
            bookDetail.getMember().overduePeriod(overDueDate.getDayOfYear());
        }

        if (bookReservation.isPresent()) {
            Optional<BookReservation> nextReservation = bookReservationRepository.findTopByBookDetailOrderByReservationRankAsc(bookDetail);
            bookDetail.renter(nextReservation.map(BookReservation::getMember).orElse(null));
            bookReservationRepository.delete(nextReservation.get());
            bookDetail.getMember().addOneMonthStatistics();

            for (BookReservation book : bookReservations) {
                book.minusReservationRank();
            }
        } else {
            bookDetail.renter(null);
            bookDetail.rentalStatus(false);
        }

        bookRentalRecord.returnTime(now);

        bookRentalRecordRepository.save(bookRentalRecord);
        bookDetailRepository.save(bookDetail);
        bookReservationRepository.saveAll(bookReservations);

        return true;
    }
}
