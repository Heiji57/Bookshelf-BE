package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book_information.entity.BookInformation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "book")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "book_seq_generator",
        sequenceName = "book_seq",
        allocationSize = 1
)
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_generator")
    @Column(name = "book_id", nullable = false, unique = true)
    private Long bookId;

    @Column(name = "book_name", nullable = false, length = 20)
    private String bookName;

    @Column(name = "book_author", nullable = false, length = 10)
    private String bookAuthor;

    @Column(name = "book_date", nullable = false, length = 20)
    private String bookDate;

    @Column(name = "book_introduction", nullable = false, length = 1000)
    private String bookIntroduction;

    @Column(name = "book_type", nullable = false, length = 20)
    private String bookType;

    @Column(name = "book_image", nullable = false, length = 200)
    private String bookImage;

    @Builder.Default
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<BookInformation> books = new ArrayList<>();
}
