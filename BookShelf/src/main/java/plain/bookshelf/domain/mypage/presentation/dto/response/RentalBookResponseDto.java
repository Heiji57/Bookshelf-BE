package plain.bookshelf.domain.mypage.presentation.dto.response;

import plain.bookshelf.domain.book.entity.BookDetail;

import java.time.LocalDateTime;

public record RentalBookResponseDto(
        Long bookId,
        String bookName,
        String bookAuthor,
        Boolean isOverDue,
        LocalDateTime OverDueTime
) {
    public static RentalBookResponseDto of(BookDetail bookDetail) {
        return new RentalBookResponseDto(
                bookDetail.getId(),
                bookDetail.getBook().getBookName(),
                bookDetail.getBook().getBookAuthor(),
                bookDetail.isOverDueStatus(),
                bookDetail.getReturnDate()
        );
    }
}
