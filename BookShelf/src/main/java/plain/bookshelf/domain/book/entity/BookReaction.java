package plain.bookshelf.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
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
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "chat", nullable = true)
    private String chat;

    @ManyToOne(optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private BookDetail bookDetail;
}
