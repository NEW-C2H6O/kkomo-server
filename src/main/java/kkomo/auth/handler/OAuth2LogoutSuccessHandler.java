package kkomo.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kkomo.auth.UserPrincipal;
import kkomo.auth.service.OAuth2Logouter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static kkomo.auth.AuthorizationRequestRedirectResolver.REDIRECT_PARAM_KEY;
import static kkomo.global.config.SecurityConfig.clients;

@Component
@RequiredArgsConstructor
public class OAuth2LogoutSuccessHandler implements LogoutSuccessHandler {

    private final OAuth2Logouter oAuth2Logouter;

    @Override
    public void onLogoutSuccess(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Authentication authentication
    ) throws IOException {
        final UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        oAuth2Logouter.logout(principal.getAccessToken());
        final String redirect = getRedirect(request);
        response.sendRedirect(redirect);
    }

    private String getRedirect(final HttpServletRequest request) {
        final String redirect = request.getParameter(REDIRECT_PARAM_KEY);
        if (!clients.contains(redirect) || !UrlUtils.isValidRedirectUrl(redirect)) {
            return "/";
        }
        return redirect;
    }
}
