package plain.bookshelf.domain.book.entity.embeddid;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberBookCommentId {

    private Long userId;

    private Long bookCommentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberBookCommentId that = (MemberBookCommentId) o;
        return userId.equals(that.userId) &&
                bookCommentId.equals(that.bookCommentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookCommentId);
    }
}
