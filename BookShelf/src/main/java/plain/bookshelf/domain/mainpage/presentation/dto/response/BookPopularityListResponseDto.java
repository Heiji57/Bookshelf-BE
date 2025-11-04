package plain.bookshelf.domain.mainpage.presentation.dto.response;

import plain.bookshelf.domain.book.entity.Book;

public record BookPopularityListResponseDto(
        Long id,
        String bookName,
        String author,
        String bookType,
        String bookImageUrl
) {
    public static BookPopularityListResponseDto of(Book book) {
        return new BookPopularityListResponseDto(
                book.getId(),
                book.getBookName(),
                book.getBookAuthor(),
                book.getBookType(),
                book.getBookImage()
        );
    }
}
