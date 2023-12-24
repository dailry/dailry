package com.daily.daily.oauth;

import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.member.domain.Member;
import com.daily.daily.oauth.constant.SocialType;
import com.daily.daily.oauth.provider.GoogleUserInfo;
import com.daily.daily.oauth.provider.KakaoUserInfo;
import com.daily.daily.oauth.provider.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class OAuthAttributes {

    private String nameAttributeKey;     // OAuth2 반환하는 유저 정보
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        if (socialType == SocialType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoUserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleUserInfo(attributes))
                .build();
    }

    public Member toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return Member.builder()
                .socialType(socialType)
                .socialId(oauth2UserInfo.getId())
                .username(oauth2UserInfo.getEmail())
                .email(oauth2UserInfo.getEmail())
                .role(MemberRole.ROLE_MEMBER)
                .build();
    }
}
