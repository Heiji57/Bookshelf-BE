package plain.bookshelf.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.BookComment;
import plain.bookshelf.domain.book.entity.repository.BookCommentRepository;
import plain.bookshelf.domain.book.exception.NotExistBookCommentException;
import plain.bookshelf.domain.book.exception.RetouchBookCommentException;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;

@Service
@RequiredArgsConstructor
public class DeleteBookCommentService {
    private final BookCommentRepository bookCommentRepository;
    private final GetCurrentMemberService getCurrentMemberService;

    public void deleteBookComment(Long commentId) {
        Member member = getCurrentMemberService.getCurrentMember();
        BookComment bookComment = bookCommentRepository.findBookCommentsById(commentId)
                .orElseThrow(NotExistBookCommentException::new);

        if (!bookComment.getMember().getId().equals(member.getId())) {
            throw new RetouchBookCommentException();
        }

        bookCommentRepository.delete(bookComment);
    }
}
