package plain.bookshelf.domain.member.entity.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserName(String username);
    boolean existsByNickName(String nickName);

    @Query(value = "SELECT m FROM Member m " +
            "JOIN BookDetail bd ON m.nickName = bd.rentalRequestMember " +
            "WHERE bd.rentalRequestMember =:nickName")
    Member findByBookDetailRenter(@Param("nickName") String nickName);

    @Query("SELECT br FROM BookReservation br WHERE br.bookDetail =:bookDetail AND br.member.nickName =:nickName")
    Member findByReservationMemberOrRentalRequestMember(@Param("bookDetail")BookDetail bookDetail, @Param("nickName") String nickName);

    @Modifying
    @Query("UPDATE Member m SET " + "m.overduePeriod = m.overduePeriod + 1 " + "WHERE m.overduePeriod >:overduePeriod")
    void findByOverduePeriod(@Param("overduePeriod") Integer overduePeriod);

    @Modifying
    @Query("UPDATE Member m SET m.monthStatistics = 0")
    void resetMonthStatistics();

    Member findMemberById(Long userId);

    @Query("SELECT m FROM Member m " +
            "LEFT JOIN m.emails e " +
            "WHERE m.userName = :username OR e.address =:username")
    Optional<Member> findByCredential(@Param("username") String username);
    Optional<Member> findByUserName(String username);
    Optional<Member> findByEmails(Email email);
    Optional<Member> findByNickName(String nickName);
}
