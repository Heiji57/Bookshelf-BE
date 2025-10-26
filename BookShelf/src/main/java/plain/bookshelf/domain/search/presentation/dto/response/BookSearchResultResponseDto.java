package plain.bookshelf.domain.search.presentation.dto.response;

import plain.bookshelf.domain.search.document.BookDocument;

import java.util.List;

public record BookSearchResultResponseDto(
    long totalHits,
    List<BookDocument> results
) { }
