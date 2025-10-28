package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.BookComment;
import plain.bookshelf.domain.book.entity.repository.BookCommentRepository;
import plain.bookshelf.domain.book.exception.NotExistBookCommentException;
import plain.bookshelf.domain.book.exception.RetouchBookCommentException;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;
import plain.bookshelf.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RetouchBookCommentException.class)
public class BookCommentRetouchService {
    private final GetCurrentMemberService getCurrentMemberService;
    private final BookCommentRepository bookCommentRepository;

    public void RetouchBookComment(String chat, Long commentId) {
        Member member = getCurrentMemberService.getCurrentMember();
        BookComment bookComment = bookCommentRepository.findBookCommentsById(commentId)
                .orElseThrow(() -> new NotExistBookCommentException(ErrorCode.BOOK_COMMENT_NOT_FOUND));

        if (!bookComment.getMember().getId().equals(member.getId())) {
            throw new RetouchBookCommentException(ErrorCode.NOT_VALID_MEMBER_INFO);
        }

        bookComment.setChat(chat);

        bookCommentRepository.save(bookComment);
    }
}
