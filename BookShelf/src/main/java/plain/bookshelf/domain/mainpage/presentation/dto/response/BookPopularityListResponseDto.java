package plain.bookshelf.domain.mainpage.presentation.dto.response;

import plain.bookshelf.domain.book.entity.BookDetail;

public record BookPopularityListResponseDto(
        Long id,
        String title,
        String author,
        String bookType,
        String bookImageUrl,
        Long rentalCount
) {
    public static BookPopularityListResponseDto of(BookDetail bookDetail) {
        return new BookPopularityListResponseDto(
                bookDetail.getId(),
                bookDetail.getBook().getBookName(),
                bookDetail.getBook().getBookAuthor(),
                bookDetail.getBook().getBookType(),
                bookDetail.getBook().getBookImageUrl(),
                bookDetail.getRentalCount()
        );
    }
}
