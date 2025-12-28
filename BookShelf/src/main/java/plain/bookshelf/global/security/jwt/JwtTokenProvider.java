package plain.bookshelf.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.entity.repository.MemberRepository;
import plain.bookshelf.domain.member.exception.NotExistUserException;
import plain.bookshelf.global.exception.ErrorCode;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final MemberRepository memberRepository;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";

    private final Key key;

    public JwtTokenProvider(JwtProperties jwtProperties, MemberRepository memberRepository) {
        this.memberRepository = memberRepository;

        log.info(">>> JWT Secret Key 로드 값: {}", jwtProperties.getSecret());
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Access Token과 Refresh Token을 함께 생성하는 메서드
    public JwtTokenDto generateToken(JwtProperties jwtProperties, Authentication authentication) {
        String username = authentication.getName();

        Member member = memberRepository.findByUserName(username)
                .orElseThrow(NotExistUserException::new);

        Long affiliationId = member.getAffiliation().getId();

        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + jwtProperties.getAccessTokenExpirationTime());
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())           // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)            // payload "auth": "ROLE_USER"
                .claim("affiliationId", affiliationId)    // payload "affiliation": "id"
                .setExpiration(accessTokenExpiresIn)            // payload "exp": 151621022 (ex)
                .signWith(key, SignatureAlgorithm.HS512)        // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                // 수정: REFRESH_TOKEN_EXPIRE_TIME 변수가 정상적으로 주입되도록 static 키워드 제거
                .setExpiration(new Date(now + jwtProperties.getRefreshTokenExpirationTime()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return JwtTokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .expiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = parseClaims(token);

        String subject = claims.getSubject();
        if (subject == null || subject.isEmpty()) {
            log.error("[+] JWT Token does not contain subject (USER ID)");

            throw new NotExistUserException();
        }
        return subject;
    }

    public Long getAffiliationIdFromToken(String token) throws IllegalAccessException {
        Claims claims = parseClaims(token);

        Object affiliationId = claims.get("affiliationId");
        if (affiliationId instanceof Number) {
            return ((Number) affiliationId).longValue();
        }

        throw new IllegalAccessException(ErrorCode.INVALID_TOKEN_VALUE.toString());
    }

    // Access Token을 받아서 Authentication 객체를 반환하는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // Access Token의 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.warn("Not correct jwtToken: {}", token);
        } catch (ExpiredJwtException e) {
            log.warn("Expired token: {}", token);
        } catch (UnsupportedJwtException e) {
            log.warn("Not supported token: {}", token);
        } catch (IllegalArgumentException e) {
            log.warn("Bad claims string: {}", token);
        }
        return false;
    }

    // 토큰 복호화
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // 만료된 토큰의 클레임도 반환 -> 누구의 만료된 access_token 인지 알기 위해 필요
            return e.getClaims();
        }
    }

    public Date getExpirationTime(String token) {
        Claims claims = parseClaims(token);

        return claims.getExpiration();
    }

    public long getRemainingExpirationTime(String token) {
        Date expiration = getExpirationTime(token);
        long now = new Date().getTime();

        return expiration.getTime() - now;
    }
}
