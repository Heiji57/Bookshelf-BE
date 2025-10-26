package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "book_name", nullable = false, length = 20)
    private String bookName;

    @Column(name = "book_author", nullable = false, length = 10)
    private String bookAuthor;

    @Column(name = "publication_date", nullable = false, length = 20)
    private LocalDate publicationDate;

    @Column(name = "book_introduction", nullable = false, length = 1000)
    private String bookIntroduction;

    @Column(name = "book_type", nullable = false, length = 20)
    private String bookType;

    @Column(name = "book_image", nullable = false, length = 200)
    private String bookImageUrl;

    @Column(name = "rental_count", nullable = false)
    @Builder.Default
    private Long rentalCount = 0L;

    @Builder.Default
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BookDetail> bookDetails = new ArrayList<>();
}
