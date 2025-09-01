package plain.bookshelf.domain.email.entity;

import jakarta.persistence.*;
import lombok.*;
import plain.bookshelf.domain.member.entity.Member;

@Entity
@Getter
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
    @Setter
    private boolean verified = false; // 인증 여부

    @Column(name = "delivered")
    @Setter
    private boolean delivered = false; // 수신 상태

    @Column(name = "verification_code")
    @Setter
    private String verificationCode;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

}

