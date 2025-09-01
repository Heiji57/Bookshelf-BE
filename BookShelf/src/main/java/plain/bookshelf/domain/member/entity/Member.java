package plain.bookshelf.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.email.entity.Email;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false, unique = true)
    private Long userId;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @Column(name = "nick_name", nullable = false, length = 20)
    private String nickName;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "user_role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Email> emails = new ArrayList<>();

    public enum Authority {
        ROLE_USER,ROLE_ADMIN
    }

    public void addEmail(Email email) {
        emails.add(email);
        email.setMember(this);
    }
}
