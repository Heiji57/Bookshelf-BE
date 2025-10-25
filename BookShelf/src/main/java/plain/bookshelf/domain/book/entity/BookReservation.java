package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.member.entity.Member;

@Entity
@Getter
@Builder
@Table(name = "book_reservation", uniqueConstraints = { @UniqueConstraint(columnNames = {"book_detail", "member"})}) // UniqueConstraint = 저 둘의 조합이 같은 값이 존재하면 안됨.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "book_reservation_seq_generator",
        sequenceName = "book_reservation_seq",
        allocationSize = 1
)
public class BookReservation {
    @EmbeddedId
    private MemberBookDetailId memberBookDetailId;

    @Column(name = "reservation_people", nullable = true)
    private String reservationPeople;

    @Column(name = "reservation_rank", nullable = false, unique = true)
    private Integer reservationRank;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bookDetail")
    private BookDetail bookDetail;
}
