package plain.bookshelf.domain.mypage.presentation.dto.response;

import java.util.List;

public record GetMyPageResponseDto(
        String userName,
        Integer rentalBookCount,
        Integer reservedBookCount,
        Integer userOverDueDate,
        Integer oneMonthStatistics,
        List<RentalBookResponseDto> rentalBook,
        List<ReservationBookResponseDto> reservationBook
) {
    public static GetMyPageResponseDto of(
            String userName,
            Integer rentalBookCount,
            Integer reservedBookCount,
            Integer userOverDueDate,
            Integer oneMonthStatistics,
            List<RentalBookResponseDto> rentalList,
            List<ReservationBookResponseDto> reservationList
    ) {
        return new GetMyPageResponseDto(
                userName,
                rentalBookCount,
                reservedBookCount,
                userOverDueDate,
                oneMonthStatistics,
                rentalList,
                reservationList
        );
    }
}
