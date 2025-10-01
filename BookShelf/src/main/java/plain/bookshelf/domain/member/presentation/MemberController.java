package plain.bookshelf.domain.member.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plain.bookshelf.domain.member.presentation.dto.MemberDeleteRequestDto;
import plain.bookshelf.domain.member.presentation.dto.MemberSignupRequestDto;
import plain.bookshelf.domain.member.presentation.dto.MemberSignupResponseDto;
import plain.bookshelf.domain.member.presentation.dto.TokenRequestDto;
import plain.bookshelf.domain.member.service.MemberService;
import plain.bookshelf.global.security.jwt.JwtTokenDto;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberSignupResponseDto> signup(@RequestBody @Valid MemberSignupRequestDto memberSignupRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(memberService.signup(memberSignupRequestDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody @Valid MemberDeleteRequestDto memberDeleteRequestDto) {
        memberService.userDelete(memberDeleteRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("Content-Type", "application/json")
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody @Valid MemberSignupRequestDto memberSignupRequestDto) {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(memberService.login(memberSignupRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<JwtTokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Authorization", "Bearer {refresh_token}")
                .body(memberService.reissue(tokenRequestDto));
    }

}
