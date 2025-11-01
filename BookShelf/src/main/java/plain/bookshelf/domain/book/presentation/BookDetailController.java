package plain.bookshelf.domain.book.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.book.presentation.dto.response.BookDetailPageResponseDto;
import plain.bookshelf.domain.book.service.BookLikeService;
import plain.bookshelf.domain.book.service.GetBookDetailPageService;
import plain.bookshelf.global.StatusResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book/{book_id}")
public class BookDetailController {

    private final GetBookDetailPageService getBookDetailPageService;
    private final BookLikeService bookLikeService;

    @GetMapping
    public ResponseEntity<?> getBookDetail(@PathVariable Long book_id, HttpServletRequest request) { // HttpServletRequest 토큰 정보 들고 오려고 사용
        BookDetailPageResponseDto bookDetailPageResponseDto = getBookDetailPageService.getBookDetailPage(book_id, request);

        return ResponseEntity.ok()
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get bookDetailPage.", bookDetailPageResponseDto));
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeBook(@PathVariable Long book_id) {
        boolean result = bookLikeService.toggleLike(book_id);

        return ResponseEntity.ok()
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully liked.", result));
    }
}
