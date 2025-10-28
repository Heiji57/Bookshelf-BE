package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.BookComment;
import plain.bookshelf.domain.book.entity.repository.BookRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookCommentWriteService {

    private final BookRepository bookRepository;
    private final GetCurrentMemberService getCurrentMemberService;

    public void bookCommentWrite(String chat, Long bookId) {
        Member member = getCurrentMemberService.getCurrentMember();
        Book book = bookRepository.findBookById(bookId);

        LocalDateTime now = LocalDateTime.now();

        BookComment.builder()
                .book(book)
                .member(member)
                .chat(chat)
                .chatTime(now)
                .build();
    }
}
