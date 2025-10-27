package plain.bookshelf.domain.book.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.BookComment;
import plain.bookshelf.domain.book.entity.BookCommentLike;
import plain.bookshelf.domain.book.entity.BookLike;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookCommentId;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookId;
import plain.bookshelf.domain.book.entity.repository.BookLikeRepository;
import plain.bookshelf.domain.book.entity.repository.BookRepository;
import plain.bookshelf.domain.book.exception.NotExistBookCommentException;
import plain.bookshelf.domain.email.entity.repository.EmailRepository;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.global.exception.ErrorCode;
import plain.bookshelf.global.security.jwt.JwtTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookLikeService {

    private final BookRepository bookRepository;
    private final BookLikeRepository bookLikeRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Member getCurrentMember() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByUserNameOrEmail(username, username)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public boolean toggleLike(Long bookId) {
        Book book = bookRepository.findByBookId(bookId);
        Member currentMember = getCurrentMember();

        MemberBookId id = new MemberBookId(bookId, currentMember.getId());

        Optional<BookLike> existingLike = bookLikeRepository.findById(id);

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
