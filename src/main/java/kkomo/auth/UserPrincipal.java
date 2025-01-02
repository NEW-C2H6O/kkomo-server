package kkomo.auth;

import kkomo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

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
        final Collection<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());
        final String role = member.getRole().name();
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        authorities.add(authority);
        return authorities;
    }

    public Long getId() {
        return member.getId();
    }

    @Override
    public String getName() {
        return member.getName();
    }

    @Override
    public String toString() {
        return String.format(
            "Name: [%s], Granted Authorities: [%s], User Attributes: [%s]",
            getName(), getAuthorities(), getAttributes()
        );
    }
}
