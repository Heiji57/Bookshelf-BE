package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.member.entity.Member;

@Entity
@Getter
@Builder
@Table(name = "book_reservation", uniqueConstraints = { @UniqueConstraint(columnNames = {"bookDetail", "member"}),
                                                        @UniqueConstraint(name = "book_reservation_rank_per_book_detail_unique", columnNames = {"bookDetail", "reservationRank"})}) // UniqueConstraint = 저 둘의 조합이 같은 값이 존재하면 안됨.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookReservation {
    @EmbeddedId
    private MemberBookDetailId memberBookDetailId;

    @Column(name = "reservation_people", nullable = false)
    private String reservationPeople;

    @Column(name = "reservation_rank", nullable = false)
    private Integer reservationRank;

    @MapsId("memberId")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @MapsId("bookDetailId")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_detail", nullable = false)
    private BookDetail bookDetail;
}
