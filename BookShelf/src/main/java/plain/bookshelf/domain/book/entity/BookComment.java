package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_comment")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@SequenceGenerator(
        name = "book_comment_seq_generator",
        sequenceName = "book_comment_seq",
        allocationSize = 1
)
public class BookComment {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_comment_seq_generator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "chat", nullable = false)
    private String chat;

    @Column(name = "chat_time", nullable = false)
    private LocalDateTime chatTime;

    @Column(name = "write_member_name", nullable = false)
    private String writeMemberName;

    @Column(name = "like_count", nullable = false)
    @Builder.Default
    private Long likeCount = 0L;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "book")
    private Book book;

    @OneToMany(mappedBy = "bookComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<BookCommentLike> bookCommentLikes = new ArrayList<>();

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if(this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void retouchComment(String comment) {
        this.chat = comment;
    }
}
