package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.affiliation.entity.Affiliation;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Builder.Default
    private boolean reservationStatus = false;

    @Column(name = "rental_status", nullable = false)
    @Builder.Default
    private boolean rentalStatus = false;

    @Column(name = "over_due_status", nullable = false)
    @Builder.Default
    private boolean overDueStatus = false;

    @Column(name = "return_date", nullable = true)
    private LocalDateTime returnDate;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @Column(name = "rental_count", nullable = false)
    @Builder.Default
    private Long rentalCount = 0L;

    @Column(name = "renter", nullable = true)
    private String renter;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "call_number", nullable = false)
    private String callNumber;

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book")
    private Book book;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation")
    private Affiliation affiliation;

    @OneToMany(mappedBy = "bookDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookRentalRecord> bookRentalRecord = new ArrayList<>();

    @OneToMany(mappedBy = "bookDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookReservation> bookReservation = new ArrayList<>();

    @OneToMany(mappedBy = "bookDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookReaction> bookReactions = new ArrayList<>();
}
