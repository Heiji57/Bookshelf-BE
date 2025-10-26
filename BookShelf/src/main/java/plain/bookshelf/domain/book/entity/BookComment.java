package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_comment")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class BookComment {
    @EmbeddedId
    private MemberBookDetailId memberBookDetailId;

    @Column(name = "chat", nullable = true)
    private String chat;

    @Column(name = "chat_time", nullable = true)
    private LocalDateTime chatTime;

    @ManyToOne(optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @ManyToOne(optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "bookDetail")
    private BookDetail bookDetail;
}
