package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookCommentId;
import plain.bookshelf.domain.member.entity.Member;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "book_comment_like")
@IdClass(MemberBookCommentId.class)
public class BookCommentLike {

    @EmbeddedId
    private MemberBookCommentId memberBookCommentId;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private BookComment bookComment;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "status", nullable = false)
    @Builder.Default
    private boolean status = true;
}
