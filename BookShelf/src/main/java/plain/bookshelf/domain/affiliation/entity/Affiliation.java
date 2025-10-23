package plain.bookshelf.domain.affiliation.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "affiliation")
@SequenceGenerator(
        name = "affiliation_seq_generator",
        sequenceName = "affiliation_seq",
        allocationSize = 1
)
public class Affiliation {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "affiliation_name")
    private String affiliationName;

    @OneToMany(mappedBy = "affiliation", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, orphanRemoval = false)
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "affiliation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookDetail> bookDetails = new ArrayList<>();
}
