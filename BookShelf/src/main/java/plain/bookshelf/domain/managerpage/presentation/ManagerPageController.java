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
public class ManagerPageController {

    private final RentalApprovalPageService approvalPageService;
    private final RentalRequestPassService requestPassService;
    private final RentalStatusPageService rentalStatusPageService;
    private final ReturnCheckService returnCheckService;
    private final SearchUserNameService searchUserNameService;

    @GetMapping("/manager/approval-page")
    public ResponseEntity<?> approveApprovalPage(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get approval page", approvalPageService.getRentalApprovalResponseDto(pageable)));
    }

    @PostMapping("/api/manager/search")
    public ResponseEntity<?> searchByNickName(@RequestParam String search) {
        String nickName = searchUserNameService.getMemberByNickName(search).getNickName();

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully search by nickname", nickName));
    }

    @GetMapping("/manager/search/rental-status/{nick_name}")
    public ResponseEntity<?> searchByNickNameResult(@PathVariable String nick_name) {
        return ResponseEntity.ok()
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully search by nickname", rentalStatusPageService.getRentalNickNameStatusPage(nick_name)));
    }

    @GetMapping("/manager/rental-status")
    public ResponseEntity<?> getRentalStatusPage(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get rental-status", rentalStatusPageService.getRentalAllStatusPage(pageable)));
    }

    @GetMapping("/manager/rental-status/overdue")
    public ResponseEntity<?> getRentalOverduePage(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully get overdue rental-status", rentalStatusPageService.getRentalOverDueStatusPage(pageable)));
    }

    @PostMapping("/api/manager/rental-pass")
    public ResponseEntity<?> passRental(@RequestParam String registrationNumber) {
        boolean result = requestPassService.rentalRequestPass(registrationNumber);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully pass rental-pass", result));
    }

    @PostMapping("/api/manager/return-check")
    public ResponseEntity<?> returnCheck(@RequestParam String registrationNumber) {
        boolean result = returnCheckService.returnCheck(registrationNumber);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully check return-check", result));
    }
}
