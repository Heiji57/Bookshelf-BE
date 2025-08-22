package plain.bookshelf.domain.user.presentation;

import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import plain.bookshelf.domain.user.entity.User;
import plain.bookshelf.domain.user.presentation.dto.UserSignupRequestDto;
import plain.bookshelf.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity signup(@RequestBody @Valid UserSignupRequestDto userSignupRequestDto) {
        User user = userService.save(userSignupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(user);
    }
}
