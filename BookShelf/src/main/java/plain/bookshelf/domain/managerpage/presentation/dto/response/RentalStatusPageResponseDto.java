package plain.bookshelf.domain.managerpage.presentation.dto.response;

import plain.bookshelf.domain.book.entity.BookDetail;

import java.time.LocalDateTime;

public record RentalStatusPageResponseDto(
        String nickName,
        String bookName,
        String registrationNumber,
        LocalDateTime rentalDate
) {
    public static RentalStatusPageResponseDto of(BookDetail bookDetail) {
        return new RentalStatusPageResponseDto(
                bookDetail.getMember().getNickName(),
                bookDetail.getBook().getBookName(),
                bookDetail.getRegistrationNumber(),
                bookDetail.getReturnDate()
        );
    }
}
