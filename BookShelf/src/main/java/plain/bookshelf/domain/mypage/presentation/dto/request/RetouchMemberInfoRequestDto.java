package plain.bookshelf.domain.mypage.presentation.dto.request;

public record RetouchMemberInfoRequestDto(
        String nickname,
        String changingPassword
) { }
