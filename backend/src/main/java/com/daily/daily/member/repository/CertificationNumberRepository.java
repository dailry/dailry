package com.daily.daily.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CertificationNumberRepository {

    private final StringRedisTemplate redisTemplate;
    private static final Duration CERTIFICATION_NUMBER_EXPIRATION = Duration.ofMinutes(10);
    private static final String PREFIX = "CERT_NUM:";

    public void saveCertificationNumber(String email, String number) {
        redisTemplate.opsForValue()
                .set(getKey(email), number, CERTIFICATION_NUMBER_EXPIRATION);
    }

    public Optional<String> getCertificationNumber(String email) {
        return Optional.ofNullable(redisTemplate.opsForValue()
                .get(getKey(email)));
    }

    private String getKey(String email) {
        return PREFIX + email;
    }

}
