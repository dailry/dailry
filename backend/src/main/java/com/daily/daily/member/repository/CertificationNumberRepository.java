package com.daily.daily.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class CertificationNumberRepository {

    private final StringRedisTemplate redisTemplate;
    private static final Duration CERTIFICATION_NUMBER_EXP = Duration.ofMinutes(10);
    private static final String PREFIX = "CERT_NUM:";

    public void saveCertificationNumber(String email, int number) {
        redisTemplate.opsForValue()
                .set(getKey(email), String.valueOf(number), CERTIFICATION_NUMBER_EXP);
    }

    private String getKey(String email) {
        return PREFIX + email;
    }
}
