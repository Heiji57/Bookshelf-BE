package plain.bookshelf.domain.book.presentation.dto.response;

import plain.bookshelf.domain.mainpage.presentation.dto.request.BookDocument;

import java.util.List;

public record BookSearchResultResponseDto(
    long totalHits,
    List<BookDocument> results
) { }
