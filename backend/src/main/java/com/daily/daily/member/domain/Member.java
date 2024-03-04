package com.daily.daily.member.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.member.constant.MemberRole;
import com.daily.daily.oauth.constant.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private SocialType socialType;

    @Builder
    public Member(String username, String password, String nickname, String email, MemberRole role, String socialId, SocialType socialType) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.socialId = socialId;
        this.socialType = socialType;
    }

    protected Member() {
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public boolean isNotSocialLoginMember() {
        return socialType == SocialType.NONE;
    }

    public boolean hasSameUsername(String otherUsername) {
        return username.equals(otherUsername);
    }
}
