package plain.bookshelf.domain.managerpage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.managerpage.service.*;
import plain.bookshelf.global.StatusResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manage")
public class ManagerPageController {

    private final RentalApprovalPageService approvalPageService;
    private final RentalStatusPageService rentalStatusPageService;

    @GetMapping("/approval-page")
    public ResponseEntity<?> approveApprovalPage(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get approval page", approvalPageService.getRentalApprovalResponseDto(pageable)));
    }

    @GetMapping("/rental-status/{nick_name}")
    public ResponseEntity<?> searchByNickNameResult(@PathVariable String nick_name) {
        return ResponseEntity.ok()
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully search by nickname", rentalStatusPageService.getRentalNickNameStatusPage(nick_name)));
    }

    @GetMapping("/rental-status")
    public ResponseEntity<?> getRentalStatusPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get rental-status", rentalStatusPageService.getRentalAllStatusPage(pageable)));
    }

    @GetMapping("/rental-status/overdue")
    public ResponseEntity<?> getRentalOverduePage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get overdue rental-status", rentalStatusPageService.getRentalOverDueStatusPage(pageable)));
    }
}
