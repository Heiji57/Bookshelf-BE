package plain.bookshelf.domain.managerpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookRentalRecord;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RentalRequestPassService {
    private final BookDetailRepository bookDetailRepository;
    private final GetCurrentMemberService getCurrentMemberService;

    public boolean rentalRequestPass(String registrationNumber){
        Member member = getCurrentMemberService.getCurrentMember();
        BookDetail bookDetail = bookDetailRepository.findBookDetailByRegistrationNumber(registrationNumber);

        LocalDateTime now = LocalDateTime.now();

        bookDetail.renter(member);
        bookDetail.rentalStatus(true);
        BookRentalRecord.builder()
                .bookDetail(bookDetail)
                .member(member)
                .rentalTime(now)
                .build();

        return true;
    }
}
