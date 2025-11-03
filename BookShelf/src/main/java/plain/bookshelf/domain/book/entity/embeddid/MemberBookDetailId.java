package plain.bookshelf.domain.book.entity.embeddid;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberBookDetailId implements Serializable {

    private Long memberId;

    private Long bookDetailId;
}
