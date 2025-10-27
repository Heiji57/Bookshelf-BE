package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookReservation;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookReservationRepository;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationBookService {
    private final BookDetailRepository bookDetailRepository;
    private final BookReservationRepository bookReservationRepository;

    private final GetCurrentMemberService getCurrentMemberService;

    public void reservationBook(String registrationNumber) {
        BookDetail bookDetail = bookDetailRepository.findBookDetailByRegistrationNumber(registrationNumber);

        Member member = getCurrentMemberService.getCurrentMemberByBookDetail();
        
        bookDetail.setRentalStatus(true);
        bookDetail.setRenter(member.getId());
        bookDetail.setReservationCount(bookDetail.getReservationCount() + 1);
        BookReservation bookReservation = BookReservation.builder()
                .bookDetail(bookDetail)
                .member(member)
                .reservationPeople(member.getNickName())
                .reservationRank(bookDetail.getReservationCount())
                .build();

        bookDetailRepository.save(bookDetail);
        bookReservationRepository.save(bookReservation);
    }
}
