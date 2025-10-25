package plain.bookshelf.domain.book.entity.embeddid;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberBookDetailId implements Serializable {

    private Long userId;

    private Long bookDetailId;

    @Override // 오버라이드하는 이유는 자바의 상속 원리 때문에 메모리 주소만을 비교한다 함.
    public boolean equals(Object o) { // 복합 키에서 동등성(Equality) 비교를 위해 equals(), hashCode()를 사용한다고 함.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberBookDetailId that = (MemberBookDetailId) o;
        return userId.equals(that.userId) &&
                bookDetailId.equals(that.bookDetailId); // 이 부분은 정보가 겹치는 부분이 있을 때 두개의 정보라고 인식하지 않고 하나의 정보라고 인식하고 들고 올 수 있도록 만들어줌. -> 같은 정보 두 번 가져와서 충돌을 일으킬 가능성을 낮춤.
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookDetailId); // 만약 userId와 bookDetailId가 같으면 항상 같은 해시 코드를 반환한다라고 함. 영속성 컨테이너 내에서 조회를 빠르게 할 수 있다는데 이 부분은 깊게 공부할 필요가 있을 듯.
    }
}
