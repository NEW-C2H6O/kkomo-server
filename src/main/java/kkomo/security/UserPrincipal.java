package kkomo.security;

import java.util.Collection;
import java.util.Map;
import kkomo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User {

    private final OAuth2User oAuth2User;
    private final Member member;

    public static UserPrincipal of(OAuth2User oAuth2User, Member member) {
        return new UserPrincipal(oAuth2User, member);
    }

    @Override
    public <A> A getAttribute(String name) {
        return oAuth2User.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    public Long getId() {
        return member.getId();
    }

    @Override
    public String getName() {
        return member.getName();
    }
}
