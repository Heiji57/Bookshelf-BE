package plain.bookshelf.domain.book.presentation.dto.response;

import plain.bookshelf.domain.book.entity.BookComment;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        String nickName,
        String chat,
        LocalDateTime chatTime,
        Long likeCount
) {

    public static ReviewResponseDto of(BookComment bookComment) {
        return new ReviewResponseDto(
                bookComment.getMember().getNickName(),
                bookComment.getChat(),
                bookComment.getChatTime(),
                bookComment.getLikeCount()
        );
    }
}
