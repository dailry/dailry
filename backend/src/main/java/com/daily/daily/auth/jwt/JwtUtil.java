package com.daily.daily.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.daily.daily.auth.dto.JwtClaimDTO;
import com.daily.daily.member.constant.MemberRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.springframework.http.HttpHeaders.SET_COOKIE;


@Component
@Slf4j
@Getter
public class JwtUtil {
    public static final String BEARER_PREFIX = "Bearer ";
    //request header
    public static final String ACCESS_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Authorization-refresh";
    //claim
    private static final String ROLE_CLAIM = "role";
    private static final String MEMBER_ID_CLAIM = "memberId";
    //subject, cookie Key
    private static final String ACCESS_TOKEN = "AccessToken";
    private static final String REFRESH_TOKEN = "RefreshToken";

    @Value("${jwt.access.expiration}")
    private long expiration;
    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpirationPeriod;

    @Value("${jwt.secret-key}")
    private String secret;
    private SecretKey secretKey;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long memberId, MemberRole role) {
        return Jwts.builder()
                .issuer("https://daily.com")
                .claim(MEMBER_ID_CLAIM, memberId)
                .claim(ROLE_CLAIM, role.name())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public String generateRefreshToken(Long memberId) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshTokenExpirationPeriod);

        return Jwts.builder()
                .claim(MEMBER_ID_CLAIM, memberId)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public JwtClaimDTO extractClaims(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Long memberId = payload.get(MEMBER_ID_CLAIM, Long.class);
        MemberRole memberRole = MemberRole.valueOf(payload.get(ROLE_CLAIM, String.class));

        return new JwtClaimDTO(memberId, memberRole);
    }

    public String createRefreshToken() {
        return JWT.create()
                .withSubject(REFRESH_TOKEN)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secret));
    }

    public void setTokensInCookie(HttpServletResponse response, String accessToken, String refreshToken) {
        ResponseCookie accessCookie = createTokenCookie(ACCESS_TOKEN, accessToken);
        ResponseCookie refreshCookie = createTokenCookie(REFRESH_TOKEN, refreshToken);

        response.addHeader(SET_COOKIE, accessCookie.toString());
        response.addHeader(SET_COOKIE, refreshCookie.toString());
    }

    private ResponseCookie createTokenCookie(String cookieName, String token) {
        return ResponseCookie.from(cookieName, token)
                .path("/")
                .secure(true)
                .httpOnly(false)
                .build();
    }
}
