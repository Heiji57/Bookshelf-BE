package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookId;
import plain.bookshelf.domain.member.entity.Member;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "book_like")
public class BookLike {
    @EmbeddedId
    private MemberBookId memberBookId;

    @MapsId("bookId")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "book")
    private Book book;

    @MapsId("memberId")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @Column(name = "status", nullable = false)
    @Builder.Default
    private boolean status = true;
}
