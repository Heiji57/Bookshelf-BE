package plain.bookshelf.domain.mainpage.presentation.dto.response;

import plain.bookshelf.domain.book.entity.Book;

import java.time.LocalDate;

public record BookRecentListResponseDto(
        Long id,
        String bookName,
        String author,
        String bookType,
        String bookImageUrl
) {
    public static BookRecentListResponseDto of(Book book) {
        return new BookRecentListResponseDto(
                book.getId(),
                book.getBookName(),
                book.getBookAuthor(),
                book.getBookType(),
                book.getBookImage()
        );
    }
}
