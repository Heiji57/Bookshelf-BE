package plain.bookshelf.domain.book.presentation.dto.request;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "books_index")
public record BookDocument(
        @Id
        String id,

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
        String bookType,

        @Field(type = FieldType.Keyword)
        String bookImageUrl,

        @Field(type = FieldType.Long)
        Long rentalCount
) {
}
