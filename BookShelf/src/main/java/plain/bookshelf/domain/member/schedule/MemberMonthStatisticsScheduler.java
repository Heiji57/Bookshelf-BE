package plain.bookshelf.domain.member.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMonthStatisticsScheduler {

    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 1 * *")
    public void oneMonthStatistics() {
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            member.resetOneMonthStatistics(0);
        }
    }
}
