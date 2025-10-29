package plain.bookshelf.domain.book.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookRentalDateScheduler {
    private final BookDetailRepository bookDetailRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void checkRentalStatus() {
        LocalDateTime today = LocalDateTime.now();

        List<BookDetail> bookDetails = bookDetailRepository.findByRentalStatusTrueAndReturnDateBefore(today);

        if (bookDetails.isEmpty()) {
            return;
        }

        for (BookDetail bookDetail : bookDetails) {
            bookDetail.markAsOverDue();
        }
    }
}
