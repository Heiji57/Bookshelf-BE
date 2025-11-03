package plain.bookshelf.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.affiliation.entity.Affiliation;
import plain.bookshelf.domain.book.entity.*;
import plain.bookshelf.domain.email.entity.Email;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(
        name = "member_seq_generator",
        sequenceName = "member_seq",
        allocationSize = 1
)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id; // DB id 값이 비어있지 않기위해 존재

    @Column(nullable = false, length = 20, unique = true)
    private String userName; // 실질적 아이디

    @Column(nullable = true, length = 20)
    @Setter
    private String nickName; // 유저 이름

    @Column(nullable = false, length = 60) // 부호화해서 저장해서 60
    private String password;

    @Column(name = "member_role", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private MemberRole authority;

    @Column(name = "profile_picture", nullable = true, length = 100)
    private String profilePicture;

    @Column(name = "month_statistics", nullable = false)
    @Builder.Default
    private Integer monthStatistics = 0;

    @Column(name = "overdue_period", nullable = false)
    @Builder.Default
    private Integer overduePeriod = 0;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Email> emails = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<BookDetail> bookDetails = new ArrayList<>();

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation", nullable = false)
    private Affiliation affiliation;

    @OneToMany(mappedBy = "member", cascade = CascadeType.DETACH, fetch = FetchType.LAZY, orphanRemoval = false)
    @Builder.Default
    private List<BookComment> bookComments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookReservation> bookReservations = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.DETACH, fetch = FetchType.LAZY, orphanRemoval = false)
    @Builder.Default
    private List<BookRentalRecord> bookRentalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookLike> bookLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<BookCommentLike> bookCommentLikes = new ArrayList<>();

    public void retouchPassword(String password) {
        this.password = password;
    }

    public void overduePeriod(Integer overduePeriod) {
        this.overduePeriod = overduePeriod;
    }

    public void addOneMonthStatistics() {
        this.monthStatistics++;
    }

    public void setProfile(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}