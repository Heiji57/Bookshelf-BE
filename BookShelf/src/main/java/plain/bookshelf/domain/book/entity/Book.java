package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "rental_status")
    private boolean rentalStatus = false;
}
