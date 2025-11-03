package plain.bookshelf.domain.book.entity.embeddid;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@EqualsAndHashCode
public class MemberBookDetailRecordId {
    private Long memberId;
    private Long bookDetailId;
    private LocalDateTime rentalTime;
}
