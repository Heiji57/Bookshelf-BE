package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookReservation;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookReservationRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.exception.MemberOverdueException;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;
import plain.bookshelf.global.exception.ErrorCode;


@Service
@RequiredArgsConstructor
public class ReservationBookService {
    private final BookDetailRepository bookDetailRepository;
    private final BookReservationRepository bookReservationRepository;

    private final GetCurrentMemberService getCurrentMemberService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reservationBook(String registrationNumber) {
        BookDetail bookDetail = bookDetailRepository.findByRegistrationNumber(registrationNumber);
        Integer maxRank = bookReservationRepository.findBookReservationMaxRankByBookDetail(bookDetail);
        Member member = getCurrentMemberService.getCurrentMember();

        if (member.getOverduePeriod() != null) {
            throw new MemberOverdueException(ErrorCode.MEMBER_OVERDUE_STATUS);
        }

        int nextRank = (maxRank == null) ? 1 : maxRank + 1;

        bookDetail.addReservationCount();
        BookReservation.builder()
                .bookDetail(bookDetail)
                .member(member)
                .reservationPeople(member.getNickName())
                .reservationRank(nextRank)
                .build();
    }
}
