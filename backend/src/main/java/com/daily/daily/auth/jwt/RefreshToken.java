package com.daily.daily.auth.jwt;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 14440)
public class RefreshToken {

    @Id
    private String refreshToken;
    private Long id;

    public RefreshToken(String refreshToken, Long id) {
        this.refreshToken = refreshToken;
        this.id = id;
    }
}
