package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
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
import plain.bookshelf.domain.member.service.GetCurrentMemberService;
import plain.bookshelf.global.exception.ErrorCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BookCommentLikeService {

    private final BookCommentRepository bookCommentRepository;
    private final BookCommentLikeRepository bookCommentLikeRepository;
    private final GetCurrentMemberService getCurrentMemberService;

    public boolean toggleLike(Long commentId) {
        Member currentMember = getCurrentMemberService.getCurrentMember();
        BookComment bookComment = bookCommentRepository.findBookCommentsById(commentId)
                .orElseThrow(() -> new NotExistBookCommentException(ErrorCode.BOOK_COMMENT_NOT_FOUND));

        MemberBookCommentId id = new MemberBookCommentId(currentMember.getId(), commentId);

        Optional<BookCommentLike> existingLike = bookCommentLikeRepository.findByMemberBookCommentId(id);

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
