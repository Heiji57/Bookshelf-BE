package plain.bookshelf.domain.mypage.presentation.dto.response;

import java.util.List;

public record GetMyPageResponseDto(
        String nickName,
        Integer rentalBookCount,
        Integer reservedBookCount,
        Integer userOverDueDate,
        Integer oneMonthStatistics,
        List<RentalBookResponseDto> rentalBook,
        List<ReservationBookResponseDto> reservationBook
) {
    public static GetMyPageResponseDto of(
            String nickName,
            Integer rentalBookCount,
            Integer reservedBookCount,
            Integer userOverDueDate,
            Integer oneMonthStatistics,
            List<RentalBookResponseDto> rentalList,
            List<ReservationBookResponseDto> reservationList
    ) {
        return new GetMyPageResponseDto(
                nickName,
                rentalBookCount,
                reservedBookCount,
                userOverDueDate,
                oneMonthStatistics,
                rentalList,
                reservationList
        );
    }
}
