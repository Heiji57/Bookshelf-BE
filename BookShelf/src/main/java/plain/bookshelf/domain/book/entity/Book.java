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

    @Column(name = "book_name", nullable = false, length = 150)
    private String bookName;

    @Column(name = "book_author", nullable = false, length = 50)
    private String bookAuthor;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "publisher", nullable = false, length = 50)
    private String publisher;

    @Column(name = "book_introduction", length = 1000)
    private String bookIntroduction;

    @Column(name = "book_type", length = 150)
    private String bookType;

    @Lob
    @Column(name = "book_image", length = 100000)
    private String bookImage;

    @Column(name = "rental_count", nullable = false)
    @Builder.Default
    private Long rentalCount = 0L;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookDetail> bookDetails = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookLike> bookLikes = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookComment> bookComments = new ArrayList<>();

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if(this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void addBookRentalCount() {
        this.rentalCount++;
    }
}
