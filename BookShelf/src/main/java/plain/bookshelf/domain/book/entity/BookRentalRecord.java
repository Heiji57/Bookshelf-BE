package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_rental_record")
@SequenceGenerator(
        name = "book_rental_record_seq_generator",
        sequenceName = "book_rental_record_seq",
        allocationSize = 1
)
public class BookRentalRecord {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "rental_time", nullable = false)
    @Builder.Default
    private LocalDateTime rentalTime = LocalDateTime.now();

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_detail")
    private BookDetail bookDetail;
}
