package plain.bookshelf.domain.book.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.book.service.*;
import plain.bookshelf.global.StatusResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final RentalBookService rentalBookService;
    private final BookCommentLikeService bookCommentLikeService;
    private final BookCommentWriteService bookCommentWriteService;
    private final BookCommentRetouchService bookCommentRetouchService;
    private final DeleteBookCommentService deleteBookCommentService;
    private final ReservationBookService reservationBookService;

    @PatchMapping("/rental")
    public ResponseEntity<?> rentalBook(@RequestParam String registrationName) {
        rentalBookService.rentalBook(registrationName);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .build();
    }

    @PatchMapping("/reservation")
    public ResponseEntity<?> reservationBook(@RequestParam String registrationName) {
        reservationBookService.reservationBook(registrationName);

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

    @PostMapping("/comment/write")
    public ResponseEntity<?> commentWrite(@RequestParam String chat, @RequestParam Long bookId) {
        bookCommentWriteService.bookCommentWrite(chat, bookId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .build();
    }

    @PatchMapping("/comment/retouch")
    public ResponseEntity<?> commentRetouch(@RequestParam String chat, @RequestParam Long commentId) {
        bookCommentRetouchService.RetouchBookComment(chat, commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .build();
    }

    @DeleteMapping("/comment/delete")
    public ResponseEntity<?> commentDelete(@RequestParam Long commentId) {
        deleteBookCommentService.deleteBookComment(commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .build();
    }
}
