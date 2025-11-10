package plain.bookshelf.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import plain.bookshelf.global.security.service.TokenBlackListService;
import plain.bookshelf.global.security.exception.AccessTokenValueNotValidException;
import plain.bookshelf.global.security.jwt.JwtTokenProvider;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlackListService tokenBlackListService;
    private final static String REFRESH_TOKEN_PREFIX = "refreshToken:";

    public void logoutService(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);

        if (accessToken == null) {
            throw new AccessTokenValueNotValidException(null);
        }

        String userId;
        try {
            userId = jwtTokenProvider.getUserIdFromToken(accessToken);
        } catch (Exception e) {
            throw new AccessTokenValueNotValidException(accessToken);
        }

        String username = String.valueOf(userId);

        Boolean deleted = redisTemplate.delete(REFRESH_TOKEN_PREFIX + username);
        if (deleted) {
            log.info("Refresh token has been deleted for user: {}", username);
        }

        // Access Token 블랙리스트 등록 (잔여 유효 기간 무효화)
        long remainingTime = jwtTokenProvider.getRemainingExpirationTime(accessToken);
        tokenBlackListService.blacklistToken(accessToken, remainingTime);

        SecurityContextHolder.clearContext();

        Date now = new Date();
        log.info("user logout time: {}", now);
    }
}
