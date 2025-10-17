package plain.bookshelf.domain.book_information.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.member.entity.Member;

@Getter
@Entity
@Table(name = "book_information")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "book_information_seq_generator",
        sequenceName = "book_information_seq",
        allocationSize = 1
)
public class BookInformation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "reservation_status", nullable = false)
    private boolean reservationStatus;

    @Column(name = "rental_status", nullable = false)
    private boolean rentalStatus;

    @Column(name = "reaction", nullable = false)
    private boolean reaction;

    @Column(name = "chat", nullable = false)
    private boolean chat;

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH)
    private Member member;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Book book;
}
