package plain.bookshelf.domain.book.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.presentation.dto.response.BookDetailPageResponseDto;
import plain.bookshelf.domain.book.service.GetBookDetailPageService;
import plain.bookshelf.global.StatusResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book/{book_id}")
public class BookDetailController {

    private final GetBookDetailPageService getBookDetailPageService;

    @GetMapping
    public ResponseEntity<?> getBookDetail(@PathVariable Long book_id) {
        BookDetailPageResponseDto bookDetailPageResponseDto = getBookDetailPageService.getBookDetailPage(book_id);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get bookDetailPage.", bookDetailPageResponseDto));
    }
}
