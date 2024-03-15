package com.daily.daily.oauth.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum SocialType {
    KAKAO("kakao"), NAVER("naver"), GOOGLE("google"), NONE("");

    private final String id;

    SocialType(String id) {
        this.id = id;
    }

    public static SocialType getSocialType(String registrationId) {

        return Arrays.stream(values())
                .filter(type -> type.id.equals(registrationId))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
