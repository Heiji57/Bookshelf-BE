package plain.bookshelf.domain.mypage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.mypage.presentation.dto.request.RetouchMemberInfoRequestDto;
import plain.bookshelf.domain.mypage.service.PostProfileService;
import plain.bookshelf.domain.mypage.service.RetouchMemberInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/{user_id}")
public class MyPageApiController {

    private final RetouchMemberInfoService retouchMemberInfoService;
    private final PostProfileService postProfileService;

    @PatchMapping("/profile-upload")
    public ResponseEntity<?> postProfile(@PathVariable Long user_id, @RequestParam String profile) {
        postProfileService.postProfile(user_id, profile);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .build();
    }

    @PatchMapping("/retouch")
    public ResponseEntity<?> retouchMemberInfo(@PathVariable Long user_id, @RequestBody RetouchMemberInfoRequestDto retouchMemberInfoRequestDto) {
        retouchMemberInfoService.retouchMemberInfo(user_id, retouchMemberInfoRequestDto);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .build();
    }
}
