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
@SequenceGenerator(
        name = "email_seq_generator",
        sequenceName = "email_seq",
        allocationSize = 1
)
public class Email {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_seq_generator")
    @Column(nullable = false)
    private Long emailId;

    @Column(nullable = false)
    private String address;

    @Column
    @Setter
    private boolean verified = false; // 인증 여부, 인증 성공 시 true

    @Column
    @Setter
    private boolean delivered = true; // 수신 상태, 기본이 수신 상태

    @Column
    @Setter
    private String verificationCode; // 이메일 인증 코드

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;
}

