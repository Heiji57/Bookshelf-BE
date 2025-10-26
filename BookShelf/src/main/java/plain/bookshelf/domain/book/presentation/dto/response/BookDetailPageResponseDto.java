package plain.bookshelf.domain.book.presentation.dto.response;

import plain.bookshelf.domain.book.entity.Book;

import java.time.LocalDate;
import java.util.List;

public record BookDetailPageResponseDto(
        String bookName,
        String publisher,
        String bookImageUrl,
        String bookIntroduction,
        String bookType,
        LocalDate bookDate,
        Long likeCount,
        List<CollectionInformationResponseDto> collectionInformationResponseDtos,
        List<ReviewResponseDto> reviewResponseDtos
) {

    public static BookDetailPageResponseDto of(String bookName,
                                               String publisher,
                                               String bookImageUrl,
                                               String bookIntroduction,
                                               String bookType,
                                               LocalDate bookDate,
                                               Long likeCount,
                                               List<CollectionInformationResponseDto> collectionInformationResponseDto,
                                               List<ReviewResponseDto> reviewResponseDtos) {
        return new BookDetailPageResponseDto(
                bookName,
                publisher,
                bookImageUrl,
                bookIntroduction,
                bookType,
                bookDate,
                likeCount,
                collectionInformationResponseDto,
                reviewResponseDtos
        );
    }
}
