package plain.bookshelf.domain.mainpage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plain.bookshelf.domain.book.presentation.dto.response.BookSearchResultResponseDto;
import plain.bookshelf.domain.mainpage.service.BookSearchService;
import plain.bookshelf.domain.mainpage.presentation.dto.response.MainListResponseDto;
import plain.bookshelf.domain.mainpage.service.MainPageListService;
import plain.bookshelf.global.dto.StatusResponseDto;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageListService mainPageListService;
    private final BookSearchService bookSearchService;

    @GetMapping("/main")
    public ResponseEntity<?> mainPageList() {
        MainListResponseDto mainListResponseDto = mainPageListService.responseRecentList();

        return ResponseEntity.ok()
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get main-page", mainListResponseDto));
    }

    @GetMapping("/api/search")
    public ResponseEntity<?> searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        BookSearchResultResponseDto bookSearchResultResponseDto = bookSearchService.searchBooks(keyword, page, size);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully search.", bookSearchResultResponseDto));
    }

    @PostMapping("/api/search/index")
    public ResponseEntity<?> searchIndex() {
        bookSearchService.indexBook();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StatusResponseDto.of(HttpStatus.CREATED, "successfully indexed.", ""));
    }
}
