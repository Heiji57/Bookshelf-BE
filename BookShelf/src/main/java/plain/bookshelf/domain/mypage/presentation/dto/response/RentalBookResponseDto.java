package plain.bookshelf.domain.mypage.presentation.dto.response;

public record RentalBookResponseDto(
        Long bookId,
        String bookName,
        String bookAuthor,
        boolean isOverDue,
        String OverDueTime
) {
    public static RentalBookResponseDto of(Long bookId, String bookName, String bookAuthor, boolean isOverDue, String OverDueTime) {
        return new RentalBookResponseDto(
                bookId,
                bookName,
                bookAuthor,
                isOverDue,
                OverDueTime
        );
    }
}
