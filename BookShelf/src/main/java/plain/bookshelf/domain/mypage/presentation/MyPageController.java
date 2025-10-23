package plain.bookshelf.domain.mypage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.mypage.presentation.dto.request.RetouchMemberInfoRequestDto;
import plain.bookshelf.domain.mypage.service.GetMyPageService;
import plain.bookshelf.domain.mypage.service.GetMyPageUserInfoService;
import plain.bookshelf.domain.mypage.service.RetouchMemberInfoService;
import plain.bookshelf.global.StatusResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage/{user_id}")
public class MyPageController {

    private final GetMyPageService getMyPageService;
    private final RetouchMemberInfoService retouchMemberInfoService;
    private final GetMyPageUserInfoService getMyPageUserInfoService;

    @GetMapping
    public ResponseEntity<?> getMyPageResponse(@PathVariable Long user_id) {

        return ResponseEntity.ok()
                .header("URI", "user_id")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get my-page.", getMyPageService.getMyPage(user_id)));
    }

    @PostMapping("/retouch")
    public ResponseEntity<?> retouchMemberInfo(@PathVariable Long user_id, @RequestBody RetouchMemberInfoRequestDto retouchMemberInfoRequestDto) {
        retouchMemberInfoService.retouchMemberInfo(user_id, retouchMemberInfoRequestDto);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .build();
    }

    @GetMapping("/get-info")
    public ResponseEntity<?> getMyPage(@PathVariable Long user_id) {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get my-page.", getMyPageUserInfoService.getMyPageUserInfo(user_id)));
    }
}
