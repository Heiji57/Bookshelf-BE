package plain.bookshelf.global.security.jwt;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import plain.bookshelf.global.exception.ErrorCode;
import plain.bookshelf.global.security.exception.CustomAuthenticationException;
import plain.bookshelf.global.security.service.TokenBlackListService;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@NonNullApi
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider tokenProvider;
    private final TokenBlackListService tokenBlackListService;

    // 실제 필터링 로직은 doFilterInternal 에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/auth/signup") ||
                requestURI.startsWith("/api/auth/login") ||
                request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = resolveToken(request);

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContextHolder 에 저장
        if (StringUtils.hasText(jwt)) {
            try {
                // 블랙리스트 토큰 체크
                if (tokenBlackListService.isBlacklisted(jwt)) {
                    request.setAttribute("exception", ErrorCode.BLACK_LIST_TOKEN);
                } else {
                    // 이 메서드(getAuthentication) 내에서 토큰이 만료되었거나 유효하지 않으면 CustomAuthenticationException 발생.
                    Authentication authentication = tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (CustomAuthenticationException e) {
                request.setAttribute("exception", e.getErrorCode());
            } catch (Exception e) {
                // 예상치 못한 예외 처리
                request.setAttribute("exception", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
        filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return "";
    }

    public Filter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, TokenBlackListService tokenBlackListService) {
        return new JwtAuthenticationFilter(jwtTokenProvider, tokenBlackListService);
    }
}