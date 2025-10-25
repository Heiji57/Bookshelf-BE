package plain.bookshelf.domain.mainpage.presentation.dto.response;

import plain.bookshelf.domain.book.entity.BookDetail;

import java.time.LocalDate;

public record BookRecentListResponseDto(
        Long id,
        String title,
        String author,
        String bookType,
        String bookImageUrl,
        LocalDate bookDate
) {
    public static BookRecentListResponseDto of(BookDetail bookDetail) {
        return new BookRecentListResponseDto(
                bookDetail.getId(),
                bookDetail.getBook().getBookName(),
                bookDetail.getBook().getBookAuthor(),
                bookDetail.getBook().getBookType(),
                bookDetail.getBook().getBookImageUrl(),
                bookDetail.getBook().getBookDate()
        );
    }
}
