package plain.bookshelf.domain.mypage.presentation.dto.response;

import plain.bookshelf.domain.book.entity.BookReservation;

public record ReservationBookResponseDto(
        Long bookId,
        String bookName,
        Integer waiting_rank
) {
    public static ReservationBookResponseDto of(BookReservation bookReservationEntity) {
        return new ReservationBookResponseDto(
                bookReservationEntity.getBookDetail().getId(),
                bookReservationEntity.getBookDetail().getBook().getBookName(),
                bookReservationEntity.getReservationRank()
        );
    }
}
