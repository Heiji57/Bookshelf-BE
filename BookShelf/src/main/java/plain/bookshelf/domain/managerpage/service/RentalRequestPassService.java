package plain.bookshelf.domain.managerpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.BookRentalRecord;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookDetailId;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookRentalRecordRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RentalRequestPassService {
    private final BookDetailRepository bookDetailRepository;
    private final MemberRepository memberRepository;
    private final BookRentalRecordRepository bookRentalRecordRepository;

    public boolean rentalRequestPass(String registrationNumber){
        BookDetail bookDetail = bookDetailRepository.findBookDetailByRegistrationNumberAndRentalRequestStatusTrue(registrationNumber);
        Member member = memberRepository.findByBookDetailRenter(bookDetail);
        Book book = bookDetail.getBook();

        MemberBookDetailId id = new MemberBookDetailId(member.getId(), bookDetail.getId());

        LocalDateTime now = LocalDateTime.now();

        bookDetail.renter(member);
        bookDetail.rentalStatus(true);
        bookDetail.rentalRequestStatus(false);
        member.addOneMonthStatistics();
        book.addBookRentalCount();
        BookRentalRecord bookRentalRecord = BookRentalRecord.builder()
                .memberBookDetailId(id)
                .bookDetail(bookDetail)
                .member(member)
                .rentalTime(now)
                .build();

        bookRentalRecordRepository.save(bookRentalRecord);

        return true;
    }
}
