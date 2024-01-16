package com.daily.daily.auth.jwt;

import com.daily.daily.auth.dto.JwtClaimDTO;
import com.daily.daily.common.dto.ExceptionResponseDTO;
import com.daily.daily.member.constant.MemberRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] authCookies = request.getCookies();
        if(authCookies == null || !isPresentAccessToken(authCookies))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = Arrays.stream(authCookies)
                .filter(name -> name.getName().equals("AccessToken"))
                .findFirst()
                .get()
                .getValue();

        if (!hasValidAuthCookie(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtUtil.validateToken(accessToken)) {
            writeErrorResponse(response);
            filterChain.doFilter(request, response);
            return;
        }

        setAuthInSecurityContext(accessToken);
        filterChain.doFilter(request, response);
    }

    private boolean isPresentAccessToken(Cookie[] authCookies) {
        return Arrays.stream(authCookies)
                .anyMatch(name -> name.getName().equals("AccessToken"));
    }

    private boolean hasValidAuthCookie(String authCookie) {
        return StringUtils.hasText(authCookie);
    }

    private void writeErrorResponse(HttpServletResponse response) throws IOException {
        ExceptionResponseDTO exceptionResponseDto = new ExceptionResponseDTO("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponseDto));
    }

    private void setAuthInSecurityContext(String accessToken) {
        JwtClaimDTO claimDTO = jwtUtil.extractClaims(accessToken);

        Long memberId = claimDTO.getMemberId();
        MemberRole role = claimDTO.getRole();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(memberId, null, List.of(role::name));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
