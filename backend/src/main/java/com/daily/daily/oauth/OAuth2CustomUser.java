package com.daily.daily.oauth;

import com.daily.daily.member.constant.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class OAuth2CustomUser extends DefaultOAuth2User {

    private String username;
    private String email;
    private MemberRole memberRole;

    public OAuth2CustomUser(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey,
                            String username, String email, MemberRole memberRole) {
        super(authorities, attributes, nameAttributeKey);
        this.username = username;
        this.email = email;
        this.memberRole = memberRole;
    }
}
