package plain.bookshelf.email.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.user.entity.User;

@Entity
@Getter
@Setter
@Table(name = "email")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id", nullable = false)
    private Long emailId;

    @Column(name = "address")
    private String address;

    @Column(name = "verified")
    private boolean verified = false; // 인증 여부

    @Column(name = "delivered")
    private boolean delivered = false; // 수신 상태

    @Column(name = "verification_code")
    private String verificationCode;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
}

