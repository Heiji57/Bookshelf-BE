package plain.bookshelf.domain.mypage.presentation.dto.response;

public record ReservationBookResponseDto(
        Long bookId,
        String bookName,
        Integer waiting_rank
) {
    public static ReservationBookResponseDto of(Long bookId, String bookName, Integer waitingRank) {
        return new ReservationBookResponseDto(
                bookId,
                bookName,
                waitingRank
        );
    }
}
