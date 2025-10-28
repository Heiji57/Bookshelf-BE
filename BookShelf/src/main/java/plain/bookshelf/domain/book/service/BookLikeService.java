package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.BookLike;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookId;
import plain.bookshelf.domain.book.entity.repository.BookLikeRepository;
import plain.bookshelf.domain.book.entity.repository.BookRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookLikeService {

    private final BookRepository bookRepository;
    private final BookLikeRepository bookLikeRepository;
    private final GetCurrentMemberService getCurrentMemberService;

    public boolean toggleLike(Long bookId) {
        Book book = bookRepository.findBookById(bookId);
        Member currentMember = getCurrentMemberService.getCurrentMember();

        MemberBookId id = new MemberBookId(bookId, currentMember.getId());

        Optional<BookLike> existingLike = bookLikeRepository.findByMemberBookId(id);

        if (existingLike.isPresent()) {
            bookLikeRepository.delete(existingLike.get());
            book.decrementLikeCount();
            return false;
        } else {
            BookLike newLike = BookLike.builder()
                    .memberBookId(id)
                    .member(currentMember)
                    .book(book)
                    .status(true)
                    .build();

            bookLikeRepository.save(newLike);
            book.incrementLikeCount();
            return true;
        }
    }
}
