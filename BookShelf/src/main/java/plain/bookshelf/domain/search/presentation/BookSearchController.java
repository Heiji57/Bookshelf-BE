package plain.bookshelf.domain.search.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.search.document.BookDocument;
import plain.bookshelf.domain.search.presentation.dto.response.BookSearchResultResponseDto;
import plain.bookshelf.domain.search.service.BookSearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book/search")
public class BookSearchController {

    private final BookSearchService bookSearchService;

    @PostMapping("/index")
    public ResponseEntity<BookDocument> index(@RequestBody BookDocument bookDocument) {
        BookDocument indexDoc = bookSearchService.indexBook(bookDocument);

        return ResponseEntity.ok(indexDoc)
                .getStatusCode() == HttpStatus.CREATED ? ResponseEntity.ok(indexDoc) : ResponseEntity.badRequest()
                .build();
    }

    @GetMapping
    public ResponseEntity<BookSearchResultResponseDto> searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        BookSearchResultResponseDto bookSearchResultResponseDto = bookSearchService.searchBooks(keyword, page, size);

        return ResponseEntity.ok(bookSearchResultResponseDto);
    }
}
