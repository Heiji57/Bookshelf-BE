package plain.bookshelf.domain.book.presentation.dto.request;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import plain.bookshelf.domain.book.entity.Book;

import java.time.LocalDate;

@Document(indexName = "books_index")
public record BookDocument(
        @Id
        Long id,

        @Field(type = FieldType.Text, analyzer = "nori")
        String bookName,

        @Field(type = FieldType.Keyword)
        String author,

        @Field(type = FieldType.Keyword)
        String publisher,

        @Field(type = FieldType.Date)
        LocalDate publicationDate,

        @Field(type = FieldType.Text, analyzer = "nori")
        String bookIntroduction,

        @Field(type = FieldType.Text, analyzer = "nori")
        String bookType
) {
    public static BookDocument of(Book book) {
        return new BookDocument(
                book.getId(),
                book.getBookName(),
                book.getBookAuthor(),
                book.getPublisher(),
                book.getPublicationDate(),
                book.getBookIntroduction(),
                book.getBookType()
        );
    }
}
