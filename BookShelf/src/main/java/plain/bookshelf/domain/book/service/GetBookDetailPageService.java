package plain.bookshelf.domain.book.service;

import jakarta.servlet.http.HttpServletRequest;
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
import plain.bookshelf.global.security.jwt.JwtTokenProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetBookDetailPageService {

    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;
    private final BookCommentRepository bookCommentRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public BookDetailPageResponseDto getBookDetailPage(Long book_id, HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);
        Long affiliationId = jwtTokenProvider.getAffiliationFromToken(accessToken).getId();

        Book book = bookRepository.findBookById(book_id);
        List<BookDetail> bookDetails = bookDetailRepository.findByBookIdAndAffiliationId(book_id, affiliationId);
        List<BookComment> bookComments = bookCommentRepository.findBookCommentByBookId(book_id);

        List<CollectionInformationResponseDto> collectionInformationResponseDtos = bookDetails.stream()
                .map(CollectionInformationResponseDto::of)
                .toList();

        List<ReviewResponseDto> reviewResponseDtos = bookComments.stream()
                .map(ReviewResponseDto::of)
                .toList();

        return BookDetailPageResponseDto.of(
                book_id,
                book.getBookName(),
                book.getPublisher(),
                book.getBookImage(),
                book.getBookImage(),
                book.getBookType(),
                book.getPublicationDate(),
                book.getLikeCount(),
                collectionInformationResponseDtos,
                reviewResponseDtos
        );
    }
}
