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
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column
    @Setter
    @Builder.Default
    private boolean verified = false; // 인증 여부, 인증 성공 시 true

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}

