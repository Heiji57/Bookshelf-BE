package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_rental_record")
public class BookRentalRecord {

    @EmbeddedId
    private MemberBookDetailId memberBookDetailId;

    @Column(name = "rental_time", nullable = false)
    @Builder.Default
    private LocalDateTime rentalTime = LocalDateTime.now();

    @Column(name = "return_time", nullable = true)
    private LocalDateTime returnTime;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_detail")
    private BookDetail bookDetail;

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;
}
