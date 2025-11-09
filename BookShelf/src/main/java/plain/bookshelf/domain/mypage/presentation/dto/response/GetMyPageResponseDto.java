package plain.bookshelf.domain.mypage.presentation.dto.response;

import java.util.List;

public record GetMyPageResponseDto(
        String profile,
        String nickName,
        Integer userOverDueDate,
        Integer oneMonthStatistics,
        List<RentalBookResponseDto> rentalBook,
        List<ReservationBookResponseDto> reservationBook
) {
    public static GetMyPageResponseDto of(
            String profile,
            String nickName,
            Integer userOverDueDate,
            Integer oneMonthStatistics,
            List<RentalBookResponseDto> rentalList,
            List<ReservationBookResponseDto> reservationList
    ) {
        return new GetMyPageResponseDto(
                profile,
                nickName,
                userOverDueDate,
                oneMonthStatistics,
                rentalList,
                reservationList
        );
    }
}
