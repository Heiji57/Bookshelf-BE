package plain.bookshelf.domain.book.entity.embeddid;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberBookDetailId implements Serializable {

    private Long userId;

    private Long bookDetailId;
}
