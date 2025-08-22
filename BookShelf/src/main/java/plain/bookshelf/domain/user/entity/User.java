package plain.bookshelf.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.email.entity.Email;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "user_role", nullable = false, length = 10)
    private String userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Email> emails = new ArrayList<>();

    public void addEmail(Email email) {
        emails.add(email);
        email.setUser(this);
    }
}
