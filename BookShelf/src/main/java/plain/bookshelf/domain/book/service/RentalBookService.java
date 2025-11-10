package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.exception.AlreadyRentalBookException;
import plain.bookshelf.domain.book.exception.AnyMoreRentalException;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.exception.MemberOverdueException;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RentalBookService {
    private final BookDetailRepository bookDetailRepository;

    private final GetCurrentMemberService getCurrentMemberService;

    private static final LocalDateTime RETURN_DATE = LocalDateTime.now().plusDays(14);

    public void rentalBook(String registrationNumber) {
        Member currentMember = getCurrentMemberService.getCurrentMember();
        BookDetail bookDetail = bookDetailRepository.findByRegistrationNumber(registrationNumber);

        LocalDateTime now = LocalDateTime.now();

        if (currentMember.getOverduePeriod() != 0) {
            throw new MemberOverdueException();
        }

        if (bookDetail.isRentalRequestStatus() || bookDetail.getMember() != null) {
            throw new AlreadyRentalBookException();
        }

        if (bookDetailRepository.findBookDetailByMember(currentMember).size() > 5 || bookDetailRepository.findByRentalRequestMember(currentMember.getNickName()).size() > 5) {
            throw new AnyMoreRentalException();
        }

        bookDetail.returnBookDate(RETURN_DATE);
        bookDetail.rentalRequestStatus(true);
        bookDetail.rentalRequestMember(currentMember.getNickName());
        bookDetail.requestDate(now);
    }
}
