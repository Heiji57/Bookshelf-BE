package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailRecordId;
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
    private MemberBookDetailRecordId memberBookDetailRecordId;

    @Column(name = "return_time", nullable = true)
    @Builder.Default
    private LocalDateTime returnTime = null;

    @MapsId("bookDetailId")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_detail")
    private BookDetail bookDetail;

    @MapsId("memberId")
    @ManyToOne(optional = true, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    public void returnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }
}
