package plain.bookshelf.domain.mypage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.mypage.presentation.dto.response.GetMyPageResponseDto;
import plain.bookshelf.domain.mypage.presentation.dto.response.GetMyPageUserInfoResponseDto;
import plain.bookshelf.domain.mypage.service.GetMyPageService;
import plain.bookshelf.domain.mypage.service.GetMyPageUserInfoService;
import plain.bookshelf.global.dto.StatusResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage/{user_id}")
public class MyPageController {

    private final GetMyPageService getMyPageService;
    private final GetMyPageUserInfoService getMyPageUserInfoService;

    @GetMapping
    public ResponseEntity<?> getMyPageResponse(@PathVariable Long user_id) {
        GetMyPageResponseDto getMyPageResponseDto = getMyPageService.getMyPage(user_id);
        return ResponseEntity.ok()
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get my-page.", getMyPageResponseDto));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getMyPage(@PathVariable Long user_id) {
        GetMyPageUserInfoResponseDto getMyPageUserInfoResponseDto = getMyPageUserInfoService.getMyPageUserInfo(user_id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get my-page.", getMyPageUserInfoResponseDto));
    }
}
