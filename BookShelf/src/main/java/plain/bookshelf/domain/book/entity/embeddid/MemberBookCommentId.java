package plain.bookshelf.domain.book.entity.embeddid;

import jakarta.persistence.Embeddable;
import jakarta.persistence.IdClass;
import lombok.*;
import plain.bookshelf.domain.book.entity.BookCommentLike;

import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Builder
public class MemberBookCommentId implements Serializable {

    private Long userId;

    private Long bookCommentId;
}
