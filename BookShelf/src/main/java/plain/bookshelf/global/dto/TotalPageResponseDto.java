package plain.bookshelf.global.dto;

import java.util.List;

public record TotalPageResponseDto(
        Integer totalPages,
        Long totalElements,
        List<?> ResponseDtoList
) {
    public static TotalPageResponseDto of(int totalPages, Long totalElements, List<?> rentalApprovalResponseDtoList) {
        return new TotalPageResponseDto(
                totalPages,
                totalElements,
                rentalApprovalResponseDtoList
        );
    }
}
