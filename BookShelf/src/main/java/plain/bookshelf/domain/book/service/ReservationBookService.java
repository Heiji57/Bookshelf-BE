package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookReservation;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookReservationRepository;
import plain.bookshelf.domain.book.exception.AlreadyReservationException;
import plain.bookshelf.domain.book.exception.AlreadyReservationOrRentalException;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.MemberOverdueException;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;


@Service
@RequiredArgsConstructor
public class ReservationBookService {
    private final BookDetailRepository bookDetailRepository;
    private final BookReservationRepository bookReservationRepository;
    private final MemberRepository memberRepository;
    private final GetCurrentMemberService getCurrentMemberService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reservationBook(String registrationNumber) {
        BookDetail bookDetail = bookDetailRepository.findByRegistrationNumberForUpdate(registrationNumber);
        Integer maxRank = bookReservationRepository.findBookReservationMaxRankByBookDetail(bookDetail);
        Member member = getCurrentMemberService.getCurrentMember();
        BookReservation bookReservation = bookReservationRepository.findByBookDetailAndMember(bookDetail, member);
        Member alreadyRentalOrReservationMember = memberRepository.findByReservationMemberOrRentalRequestMember(bookDetail, member.getNickName());

        if (alreadyRentalOrReservationMember != null) {
            throw new AlreadyReservationOrRentalException();
        }

        if (bookReservation != null) {
            throw new AlreadyReservationException();
        }

        if (member.getOverduePeriod() != 0) {
            throw new MemberOverdueException();
        }

        int nextRank = (maxRank == null) ? 1 : maxRank + 1;

        MemberBookDetailId id = new MemberBookDetailId(member.getId(), bookDetail.getId());

        bookDetail.addReservationCount();
        BookReservation bookReservationBuild = BookReservation.builder()
                .memberBookDetailId(id)
                .bookDetail(bookDetail)
                .member(member)
                .reservationPeople(member.getNickName())
                .reservationRank(nextRank)
                .build();

        bookReservationRepository.save(bookReservationBuild);
    }
}
