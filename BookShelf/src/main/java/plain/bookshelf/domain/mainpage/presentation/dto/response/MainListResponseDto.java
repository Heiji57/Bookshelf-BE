package plain.bookshelf.domain.mainpage.presentation.dto.response;

import java.util.List;

public record MainListResponseDto(
        List<BookPopularityListResponseDto> bookPopularityListResponseDtoList,
        List<BookRecentListResponseDto> bookRecentListResponseDtoList
) {
    public static MainListResponseDto of(List<BookPopularityListResponseDto> bookPopularityListResponseDtoList,
                                         List<BookRecentListResponseDto> bookRecentListResponseDtoList) {
        return new MainListResponseDto(
                bookPopularityListResponseDtoList,
                bookRecentListResponseDtoList);
    }
}
