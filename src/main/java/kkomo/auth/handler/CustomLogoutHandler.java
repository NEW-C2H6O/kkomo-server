package kkomo.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kkomo.auth.UserPrincipal;
import kkomo.auth.service.OAuth2Logouter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final OAuth2Logouter oAuth2Logouter;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        oAuth2Logouter.logout(principal.getAccessToken());
    }
}
