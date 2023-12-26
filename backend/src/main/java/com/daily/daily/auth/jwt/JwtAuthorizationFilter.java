package com.daily.daily.auth.jwt;

import com.daily.daily.auth.dto.JwtClaimDTO;
import com.daily.daily.common.dto.ExceptionResponseDTO;
import com.daily.daily.member.constant.MemberRole;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.daily.daily.auth.jwt.JwtUtil.BEARER_PREFIX;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (!hasValidAuthHeader(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authHeader.substring(BEARER_PREFIX.length());

        if (!jwtUtil.validateToken(accessToken)) {
            writeErrorResponse(response);
            filterChain.doFilter(request, response);
            return;
        }

        setAuthInSecurityContext(accessToken);
        filterChain.doFilter(request, response);
    }

    private boolean hasValidAuthHeader(String authHeader) {
        return StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX);
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
