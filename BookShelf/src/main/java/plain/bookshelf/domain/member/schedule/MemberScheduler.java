package plain.bookshelf.domain.member.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberScheduler {

    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 1 * *")
    public void oneMonthStatistics() {
        memberRepository.resetMonthStatistics();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void oneDayOverDuePeriod() {
        memberRepository.findByOverduePeriod(0);
    }
}
