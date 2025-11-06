package plain.bookshelf.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.domain.member.presentation.dto.request.TokenRequestDto;
import plain.bookshelf.global.exception.ErrorCode;
import plain.bookshelf.global.security.entity.RefreshToken;
import plain.bookshelf.global.security.entity.repository.RefreshTokenRepository;
import plain.bookshelf.global.security.exception.RefreshTokenValueNotValidException;
import plain.bookshelf.global.security.jwt.JwtProperties;
import plain.bookshelf.global.security.jwt.JwtTokenDto;
import plain.bookshelf.global.security.jwt.JwtTokenProvider;

@RequiredArgsConstructor
@Service
public class ReissueService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    /*
    Redis 사용해서 Transactional 필요 없음
    */
    public JwtTokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. RefreshToken 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.refreshToken())) {
            throw new RefreshTokenValueNotValidException(ErrorCode.REFRESH_TOKEN_NOT_MATCH, tokenRequestDto.refreshToken());
        }

        // 2. Access Token 예시 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.accessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new NotExistUserException(ErrorCode.MEMBER_NOT_FOUND));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.refreshToken())) {
            throw new RefreshTokenValueNotValidException(ErrorCode.REFRESH_TOKEN_NOT_MATCH, tokenRequestDto.refreshToken());
        }

        // 5. 새로운 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(jwtProperties, authentication);

        // 6. 저장소 정보 업데이트 및 TTL 설정
        refreshToken.updateValue(jwtTokenDto.getRefreshToken());
        refreshTokenRepository.save(refreshToken); // Redis에 저장, TTL은 @TimeToLive 어노테이션으로 자동 설정됨

        // 토큰 발급
        return jwtTokenDto;
    }
}
