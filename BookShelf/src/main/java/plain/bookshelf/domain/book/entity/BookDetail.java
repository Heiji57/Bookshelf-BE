package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.affiliation.entity.Affiliation;
import plain.bookshelf.domain.member.entity.Member;

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

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_detail_seq_generator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "rental_status", nullable = false)
    @Builder.Default
    private boolean rentalStatus = false;

    @Column(name = "rental_request_date", nullable = true)
    private LocalDateTime rentalRequestDate;

    @Column(name = "rental_request_status", nullable = false)
    @Builder.Default
    private boolean rentalRequestStatus = false;

    @Column(name = "over_due_status", nullable = false)
    @Builder.Default
    private boolean overDueStatus = false;

    @Column(name = "return_date", nullable = true)
    private LocalDateTime returnDate;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "call_number", nullable = false)
    private String callNumber;

    @Column(name = "reservation_count", nullable = false)
    private Integer reservationCount;

    @Column(name = "rental_request_member", nullable = true)
    private String rentalRequestMember;

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member; // 대여자

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "book")
    private Book book;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation")
    private Affiliation affiliation;

    @OneToMany(mappedBy = "bookDetail", cascade = CascadeType.DETACH, fetch = FetchType.LAZY, orphanRemoval = false)
    @Builder.Default
    private List<BookRentalRecord> bookRentalRecord = new ArrayList<>();

    @OneToMany(mappedBy = "bookDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookReservation> bookReservation = new ArrayList<>();

    public void markAsOverDue() {
        if (this.rentalStatus) {
            this.overDueStatus = true;
        }
    }

    public void returnBookDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void rentalRequestStatus(boolean rentalRequestStatus) {
        this.rentalRequestStatus = rentalRequestStatus;
    }

    public void renter(Member member) {
        this.member = member;
    }

    public void addReservationCount() {
        this.reservationCount++;
    }

    public void rentalRequestMember(String rentalRequestMember) {
        this.rentalRequestMember = rentalRequestMember;
    }

    public void requestDate(LocalDateTime requestDate) {
        this.rentalRequestDate = requestDate;
    }

    public void rentalStatus(boolean rentalStatus) {
        this.rentalStatus = rentalStatus;
    }
}
