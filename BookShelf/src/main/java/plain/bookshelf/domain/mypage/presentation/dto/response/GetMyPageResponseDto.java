package plain.bookshelf.domain.mypage.presentation.dto.response;

import java.util.List;

public record GetMyPageResponseDto(
        String nickName,
        Integer userOverDueDate,
        Integer oneMonthStatistics,
        List<RentalBookResponseDto> rentalBook,
        List<ReservationBookResponseDto> reservationBook
) {
    public static GetMyPageResponseDto of(
            String nickName,
            Integer userOverDueDate,
            Integer oneMonthStatistics,
            List<RentalBookResponseDto> rentalList,
            List<ReservationBookResponseDto> reservationList
    ) {
        return new GetMyPageResponseDto(
                nickName,
                userOverDueDate,
                oneMonthStatistics,
                rentalList,
                reservationList
        );
    }
}
