package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.BookComment;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.repository.BookCommentRepository;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookRepository;
import plain.bookshelf.domain.book.presentation.dto.response.BookDetailPageResponseDto;
import plain.bookshelf.domain.book.presentation.dto.response.CollectionInformationResponseDto;
import plain.bookshelf.domain.book.presentation.dto.response.ReviewResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetBookDetailPageService {

    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;
    private final BookCommentRepository bookCommentRepository;

    public BookDetailPageResponseDto getBookDetailPage(Long book_id) {
        Book book = bookRepository.findByBookId(book_id);
        List<BookDetail> bookDetails = bookDetailRepository.findBookDetailByBookId(book_id);
        List<BookComment> bookComments = bookCommentRepository.findBookCommentByBookId(book_id);

        List<CollectionInformationResponseDto> collectionInformationResponseDtos = bookDetails.stream()
                .map(CollectionInformationResponseDto::of)
                .toList();

        List<ReviewResponseDto> reviewResponseDtos = bookComments.stream()
                .map(ReviewResponseDto::of)
                .toList();

        return BookDetailPageResponseDto.of(
                book.getBookName(),
                book.getPublisher(),
                book.getBookImageUrl(),
                book.getBookImageUrl(),
                book.getBookType(),
                book.getPublicationDate(),
                book.getLikeCount(),
                collectionInformationResponseDtos,
                reviewResponseDtos
        ); // 대출하기, 예약하기, 좋아요, 댓글 기능만들기
    }
}
