package plain.bookshelf.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookReservation;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookReservationRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.domain.mypage.presentation.dto.response.GetMyPageResponseDto;
import plain.bookshelf.domain.mypage.presentation.dto.response.RentalBookResponseDto;
import plain.bookshelf.domain.mypage.presentation.dto.response.ReservationBookResponseDto;
import plain.bookshelf.global.exception.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMyPageService {
    private final BookDetailRepository bookDetailRepository;
    private final MemberRepository memberRepository;
    private final BookReservationRepository bookReservationRepository;

    public GetMyPageResponseDto getMyPage(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));

        List<BookDetail> rentals = bookDetailRepository.findBookDetailByMember(member);

        Integer rentalBookCount = rentals.size();

        List<BookReservation> bookReservations = bookReservationRepository.findBookReservationByMember(member);

        Integer reservationBookCount = bookReservations.size();

        Integer overdueDate = member.getOverduePeriod();

        Integer oneMonthStatistics = member.getMonthStatistics();
        
        List<RentalBookResponseDto> rentalDtos = rentals.stream()
                .map(this::toRental)
                .toList();

        List<ReservationBookResponseDto> reservationDtos = bookReservations.stream()
                .map(this::toReservation)
                .toList();

        return GetMyPageResponseDto.of(
                member.getUserName(),
                rentalBookCount,
                reservationBookCount,
                overdueDate,
                oneMonthStatistics,
                rentalDtos,
                reservationDtos
        );
    }

    private RentalBookResponseDto toRental(BookDetail bookDetailEntity) { // 무조건 하나의 인수만 들어가야함.

        return RentalBookResponseDto.of(
                bookDetailEntity.getId(),
                bookDetailEntity.getBook().getBookName(),
                bookDetailEntity.getBook().getBookAuthor(),
                bookDetailEntity.isOverDueStatus(),
                bookDetailEntity.getReturnDate().toString()
        );
    }

    private ReservationBookResponseDto toReservation(BookReservation bookReservationEntity) {
        return ReservationBookResponseDto.of(
                bookReservationEntity.getId(),
                bookReservationEntity.getReservationPeople(),
                bookReservationEntity.getReservationRank()
        );
    }
}
