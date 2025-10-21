package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.affiliation.entity.Affiliation;
import plain.bookshelf.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "book_detail")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "book_detail_seq_generator",
        sequenceName = "book_detail_seq",
        allocationSize = 1
)
public class BookDetail {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "reservation_status", nullable = false)
    private boolean reservationStatus;

    @Column(name = "rental_status", nullable = false)
    private boolean rentalStatus;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @Column(name = "rental_count", nullable = false)
    @Builder.Default
    private Long rental_count = 0L;

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book")
    private Book book;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation")
    private Affiliation affiliation;

    @OneToMany(mappedBy = "book_detail", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BookRentalRecord> bookRentalRecord = new ArrayList<>();
}
