package plain.bookshelf.domain.book.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import plain.bookshelf.domain.affiliation.entity.Affiliation;
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
        Affiliation affiliation = jwtTokenProvider.getAffiliationFromToken(accessToken);

        Book book = bookRepository.findByBookId(book_id);
        List<BookDetail> bookDetails = bookDetailRepository.findByBookIdAndAffiliation(book_id, affiliation);
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
        );
    }
}
