package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.member.entity.Member;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@SequenceGenerator(
        name = "book_reaction_seq_generator",
        sequenceName = "book_reaction_seq",
        allocationSize = 1
)
public class BookReaction {
    @EmbeddedId
    private MemberBookDetailId memberBookDetailId;

    @Column(name = "chat", nullable = true)
    private String chat;

    @ManyToOne(optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @ManyToOne(optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "bookDetail")
    private BookDetail bookDetail;
}
