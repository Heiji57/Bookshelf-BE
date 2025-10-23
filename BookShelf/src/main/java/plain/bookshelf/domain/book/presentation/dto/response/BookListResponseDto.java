package plain.bookshelf.domain.book.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookListResponseDto {

    private String title;

    private String author;

    private String bookType;

    private String bookImageUrl;
}
