package plain.bookshelf.domain.managerpage.presentation.dto.response;

import plain.bookshelf.domain.book.entity.BookDetail;

import java.time.LocalDateTime;

public record RentalApprovalResponseDto(
        String bookName,
        String registrationNumber,
        String nickName,
        LocalDateTime requestDate
) {
    public static RentalApprovalResponseDto of(BookDetail bookDetail) {
        return new RentalApprovalResponseDto(
                bookDetail.getBook().getBookName(),
                bookDetail.getRegistrationNumber(),
                bookDetail.getRentalRequestMember(),
                bookDetail.getRentalRequestDate()
        );
    }
}
