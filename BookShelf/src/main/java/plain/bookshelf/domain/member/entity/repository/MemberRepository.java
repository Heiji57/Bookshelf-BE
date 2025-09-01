package plain.bookshelf.domain.member.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserName(String username);
    boolean existsByNickName(String nickName);

    Optional<Member> findByUserName(String username);
}
