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

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMyPageService {
    private final BookDetailRepository bookDetailRepository;
    private final MemberRepository memberRepository;
    private final BookReservationRepository bookReservationRepository;

    public GetMyPageResponseDto getMyPage(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(NotExistUserException::new);

        List<BookDetail> rentals = bookDetailRepository.findBookDetailByMember(member);

        List<BookReservation> bookReservations = bookReservationRepository.findBookReservationByMember(member);

        Integer overdueDate = member.getOverduePeriod();
        Integer oneMonthStatistics = member.getMonthStatistics();

        List<RentalBookResponseDto> rentalDtos = rentals.stream()
                .map(RentalBookResponseDto::of)
                .toList();

        List<ReservationBookResponseDto> reservationDtos = bookReservations.stream()
                .map(ReservationBookResponseDto::of)
                .toList();

        return GetMyPageResponseDto.of(
                member.getProfilePicture(),
                member.getNickName(),
                overdueDate,
                oneMonthStatistics,
                rentalDtos,
                reservationDtos
        );
    }
}
