package plain.bookshelf.domain.mainpage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plain.bookshelf.domain.mainpage.presentation.dto.response.MainListResponseDto;
import plain.bookshelf.domain.mainpage.service.MainPageListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainPageController {

    private final MainPageListService mainPageListService;

    @GetMapping
    public ResponseEntity<?> mainPageList() {
        MainListResponseDto mainListResponseDto = mainPageListService.responseRecentList();

        return ResponseEntity.ok()
                .body(mainListResponseDto);
    }
}
