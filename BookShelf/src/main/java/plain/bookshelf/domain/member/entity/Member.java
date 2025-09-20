package plain.bookshelf.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
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
        initialValue = 1,
        allocationSize = 1
)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    @Column(nullable = false, updatable = false, unique = true)
    private Long userId; // DB id 값이 비어있지 않기위해 존재

    @Column(nullable = false, length = 20, unique = true)
    private String userName; // 실질적 아이디

    @Column(nullable = false, length = 20)
    private String nickName; // 유저 이름

    @Column(nullable = false, length = 60)
    private String password;

    @Column(name = "member_role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Email> emails = new ArrayList<>();

    public enum Authority {
        ROLE_USER,ROLE_MANAGER,ROLE_GUEST
    }

    public void addEmail(Email email) {
        emails.add(email);
        email.setMember(this);
    }
}
