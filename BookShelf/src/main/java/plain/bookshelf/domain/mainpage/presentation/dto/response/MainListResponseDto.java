package plain.bookshelf.domain.mainpage.presentation.dto.response;

import java.util.List;

public record MainListResponseDto(
        Long memberId,
        String profile,
        List<BookPopularityListResponseDto> bookPopularityListResponseDtoList,
        List<BookRecentListResponseDto> bookRecentListResponseDtoList
) {
    public static MainListResponseDto of(Long memberId,
                                         String profile,
                                         List<BookPopularityListResponseDto> bookPopularityListResponseDtoList,
                                         List<BookRecentListResponseDto> bookRecentListResponseDtoList) {
        return new MainListResponseDto(
                memberId,
                profile,
                bookPopularityListResponseDtoList,
                bookRecentListResponseDtoList
        );
    }
}
