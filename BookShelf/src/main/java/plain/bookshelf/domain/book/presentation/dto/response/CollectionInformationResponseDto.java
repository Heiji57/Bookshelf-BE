package plain.bookshelf.domain.book.presentation.dto.response;

import plain.bookshelf.domain.book.entity.BookDetail;

public record CollectionInformationResponseDto(
        String affiliation,
        String registrationNumber,
        String callNumber,
        Boolean rentalStatus
) {

    public static CollectionInformationResponseDto of(BookDetail bookDetail) {
        return new CollectionInformationResponseDto(
                bookDetail.getAffiliation().getAffiliationName(),
                bookDetail.getRegistrationNumber(),
                bookDetail.getCallNumber(),
                bookDetail.isRentalStatus()
        );
    }
}
