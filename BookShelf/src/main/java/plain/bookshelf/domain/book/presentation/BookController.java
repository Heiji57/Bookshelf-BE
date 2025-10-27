package plain.bookshelf.domain.book.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.book.service.BookCommentLikeService;
import plain.bookshelf.domain.book.service.RentalBookService;
import plain.bookshelf.global.StatusResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final RentalBookService rentalBookService;
    private final BookCommentLikeService bookCommentLikeService;

    @PatchMapping("/rental")
    public ResponseEntity<?> rentalBook(@RequestParam String registrationName) {
        rentalBookService.rentalBook(registrationName);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .build();
    }

    @PatchMapping("/rental")
    public ResponseEntity<?> reservationBook(@RequestParam String registrationName) {
        rentalBookService.rentalBook(registrationName);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .build();
    }

    @PostMapping("/comment/like")
    public ResponseEntity<?> commentLike(@RequestParam Long commentId) {
        boolean result = bookCommentLikeService.toggleLike(commentId);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully Liked", result));
    }
}
