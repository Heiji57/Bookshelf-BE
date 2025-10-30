package plain.bookshelf.domain.mainpage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import plain.bookshelf.domain.mainpage.presentation.dto.response.MainListResponseDto;
import plain.bookshelf.domain.mainpage.service.GetMyPageByProfileService;
import plain.bookshelf.domain.mainpage.service.MainPageListService;
import plain.bookshelf.global.StatusResponseDto;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageListService mainPageListService;
    private final GetMyPageByProfileService getMyPageByProfileService;

    @GetMapping("/main")
    public ResponseEntity<?> mainPageList() {
        MainListResponseDto mainListResponseDto = mainPageListService.responseRecentList();

        return ResponseEntity.ok()
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get main-page", mainListResponseDto));
    }

    @GetMapping("/api/profile")
    public ResponseEntity<?> profile() {
        Long id = getMyPageByProfileService.getCurrentMember().getId();

        return ResponseEntity.ok()
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully profile information", id));
    }
}
