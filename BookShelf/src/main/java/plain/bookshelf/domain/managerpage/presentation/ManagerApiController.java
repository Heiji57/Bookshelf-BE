package plain.bookshelf.domain.managerpage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plain.bookshelf.domain.managerpage.service.RentalRequestPassService;
import plain.bookshelf.domain.managerpage.service.ReturnCheckService;
import plain.bookshelf.domain.managerpage.service.SearchUserNameService;
import plain.bookshelf.global.StatusResponseDto;

@RestController
@RequiredArgsConstructor
public class ManagerApiController {

    private final RentalRequestPassService requestPassService;
    private final ReturnCheckService returnCheckService;
    private final SearchUserNameService searchUserNameService;

    @PostMapping("/api/manager/search")
    public ResponseEntity<?> searchByNickName(@RequestParam String search) {
        String nickName = searchUserNameService.getMemberByNickName(search).getNickName();

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully search by nickname", nickName));
    }

    @PatchMapping("/api/manager/rental-pass")
    public ResponseEntity<?> passRental(@RequestParam String registrationNumber) {
        boolean result = requestPassService.rentalRequestPass(registrationNumber);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully pass rental-pass", result));
    }

    @PatchMapping("/api/manager/return-check")
    public ResponseEntity<?> returnCheck(@RequestParam String registrationNumber) {
        boolean result = returnCheckService.returnCheck(registrationNumber);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK, "successfully check return-check", result));
    }
}
