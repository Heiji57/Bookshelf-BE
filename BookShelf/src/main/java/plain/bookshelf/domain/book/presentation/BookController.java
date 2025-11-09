package plain.bookshelf.domain.book.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.book.presentation.dto.request.BookChatRequestDto;
import plain.bookshelf.domain.book.service.*;
import plain.bookshelf.global.dto.StatusResponseDto;

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
    public ResponseEntity<?> rentalBook(@RequestParam String registrationNumber) {
        rentalBookService.rentalBook(registrationNumber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.CREATED, "successfully rental book", ""));
    }

    @PatchMapping("/reservation")
    public ResponseEntity<?> reservationBook(@RequestParam String registrationNumber) {
        reservationBookService.reservationBook(registrationNumber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.CREATED, "successfully reservation book", ""));
    }

    @PostMapping("/comment/like")
    public ResponseEntity<?> commentLike(@RequestParam Long commentId) {
        boolean result = bookCommentLikeService.toggleLike(commentId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.CREATED, "successfully comment liked", result));
    }

    @PostMapping("/comment/write")
    public ResponseEntity<?> commentWrite(@RequestBody BookChatRequestDto bookChatRequestDto, @RequestParam Long bookId) {
        bookCommentWriteService.bookCommentWrite(bookChatRequestDto.chat(), bookId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.CREATED, "successfully comment written", ""));
    }

    @PatchMapping("/comment/retouch")
    public ResponseEntity<?> commentRetouch(@RequestBody BookChatRequestDto bookChatRequestDto, @RequestParam Long commentId) {
        bookCommentRetouchService.RetouchBookComment(bookChatRequestDto.chat(), commentId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.CREATED, "successfully comment retouched", ""));
    }

    @DeleteMapping("/comment/delete")
    public ResponseEntity<?> commentDelete(@RequestParam Long commentId) {
        deleteBookCommentService.deleteBookComment(commentId);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.NO_CONTENT, "successfully deleted", ""));
    }
}
