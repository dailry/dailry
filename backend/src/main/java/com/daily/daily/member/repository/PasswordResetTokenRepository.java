package com.daily.daily.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

/**
 * 비밀번호 재설정을 링크를 보낼 때, 인증을 위한 토큰값과 회원 ID 값을 저장합니다.
 * <PASSWORD_RESET_TOKEN, MEMBER_ID>
 */
@Repository
@RequiredArgsConstructor
public class PasswordResetTokenRepository {

    private final RedisTemplate<String, Long> redisTemplate;
    private static final Duration TOKEN_EXPIRATION = Duration.ofMinutes(30);
    private static final String PREFIX = "PASSWORD_RESET_TOKEN:";

    public void save(String token, Long memberId) {
        redisTemplate.opsForValue()
                .set(getKey(token), memberId, TOKEN_EXPIRATION);
    }

    public Optional<Long> getMemberIdByToken(String token) {
        Long memberId = redisTemplate.opsForValue()
                .get(getKey(token));

        return Optional.ofNullable(memberId);
    }

    public String createRandomToken() {
        String token = UUID.randomUUID().toString();
        if (alreadyExistToken(token)) return createRandomToken();
        return token;
    }

    private boolean alreadyExistToken(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getKey(token)));
    }

    private String getKey(String email) {
        return PREFIX + email;
    }
}
