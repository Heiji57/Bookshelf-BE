package plain.bookshelf.domain.mypage.presentation.dto.response;

public record GetMyPageUserInfoResponseDto(
        String nickName,
        String address
) {
    public static GetMyPageUserInfoResponseDto of(
            String nickName,
            String address
    ) {
        return new GetMyPageUserInfoResponseDto(nickName, address);
    }
}
