package plain.bookshelf.domain.book.entity.embeddid;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MemberBookId {
    private Long bookId;
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberBookId that = (MemberBookId) o;
        return userId.equals(that.userId) &&
                bookId.equals(that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }
}
