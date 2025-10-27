package plain.bookshelf.domain.book.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.BookComment;
import plain.bookshelf.domain.book.entity.BookCommentLike;
import plain.bookshelf.domain.book.entity.embeddid.MemberBookCommentId;
import plain.bookshelf.domain.book.entity.repository.BookCommentLikeRepository;
import plain.bookshelf.domain.book.entity.repository.BookCommentRepository;
import plain.bookshelf.domain.book.exception.NotExistBookCommentException;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.global.exception.ErrorCode;
import plain.bookshelf.global.security.jwt.JwtTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BookCommentLikeService {

    private final BookCommentRepository bookCommentRepository;
    private final BookCommentLikeRepository bookCommentLikeRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private Member getCurrentMember() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByUserNameOrEmail(username, username)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public boolean toggleLike(Long commentId) {
        Member currentMember = getCurrentMember();
        BookComment bookComment = bookCommentRepository.findBookCommentsByCommentId(commentId)
                .orElseThrow(() -> new NotExistBookCommentException(ErrorCode.BOOK_COMMENT_NOT_FOUND));

        MemberBookCommentId id = new MemberBookCommentId(currentMember.getId(), commentId);

        Optional<BookCommentLike> existingLike = bookCommentLikeRepository.findById(id);

        if (existingLike.isPresent()) {
            bookCommentLikeRepository.delete(existingLike.get());
            bookComment.decrementLikeCount();
            return false;
        } else {
            BookCommentLike newLike = BookCommentLike.builder()
                    .memberBookCommentId(id)
                    .member(currentMember)
                    .bookComment(bookComment)
                    .status(true)
                    .build();

            bookCommentLikeRepository.save(newLike);
            bookComment.incrementLikeCount();
            return true;
        }
    }
}
