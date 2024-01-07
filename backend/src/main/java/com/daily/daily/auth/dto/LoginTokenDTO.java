package com.daily.daily.auth.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "LogoutAccessToken")
@AllArgsConstructor
@Builder
public class LoginTokenDTO {
    @Id
    private String id;

    private Long userId;

    @TimeToLive
    private Long expiration;

    public static LoginTokenDTO of(String accessToken,  Long userId, Long remainingMilliSeconds) {
        return LoginTokenDTO.builder()
                .id(accessToken)
                .userId(userId)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }

}
