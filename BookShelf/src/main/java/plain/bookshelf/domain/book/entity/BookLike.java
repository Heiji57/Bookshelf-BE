package plain.bookshelf.domain.book.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "book_like")
public class BookLike {
    @EmbeddedId
    MemberBookDetailId memberBookDetailId;
}
