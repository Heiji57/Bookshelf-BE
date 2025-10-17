package plain.bookshelf.domain.member.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.member.presentation.dto.MemberDeleteRequestDto;
import plain.bookshelf.domain.member.presentation.dto.MemberSignupRequestDto;
import plain.bookshelf.domain.member.presentation.dto.TokenRequestDto;
import plain.bookshelf.domain.member.service.*;
import plain.bookshelf.global.StatusResponseDto;
import plain.bookshelf.global.security.service.TokenBlackListService;
import plain.bookshelf.global.security.jwt.JwtTokenProvider;
import plain.bookshelf.domain.member.service.LogoutService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class MemberController {

    private final SigunupService sigunupService;
    private final DeleteUserService deleteUserService;
    private final LoginService loginService;
    private final ReissueService reissueService;
    private final LogoutService logoutService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberSignupRequestDto memberSignupRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.CREATED,"successfully signup user.",sigunupService.signup(memberSignupRequestDto)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid MemberDeleteRequestDto memberDeleteRequestDto) {
        deleteUserService.userDelete(memberDeleteRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("Content-Type", "application/json")
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberSignupRequestDto memberSignupRequestDto) {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of(HttpStatus.OK,"successfully delete user.", loginService.login(memberSignupRequestDto)));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        logoutService.deleteRefreshToken(request);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(StatusResponseDto.of( HttpStatus.NO_CONTENT,"successfully logged out.", ""));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Authorization", "Bearer {refresh_token}")
                .body(StatusResponseDto.of(HttpStatus.OK,"successfully check refresh token valid.", reissueService.reissue(tokenRequestDto)));
    }

}
