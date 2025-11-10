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

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RetouchBookCommentException.class)
public class BookCommentRetouchService {
    private final GetCurrentMemberService getCurrentMemberService;
    private final BookCommentRepository bookCommentRepository;

    public void RetouchBookComment(String chat, Long commentId) {
        Member member = getCurrentMemberService.getCurrentMember();
        BookComment bookComment = bookCommentRepository.findBookCommentsById(commentId)
                .orElseThrow(NotExistBookCommentException::new);

        if (!bookComment.getMember().getId().equals(member.getId())) {
            throw new RetouchBookCommentException();
        }

        bookComment.retouchComment(chat);
        bookCommentRepository.save(bookComment);
    }
}
