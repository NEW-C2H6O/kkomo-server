package kkomo.auth.service;

import kkomo.auth.UserPrincipal;
import kkomo.auth.domain.SignUpCommand;
import kkomo.auth.domain.SignUpManager;
import kkomo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {

    private static final String KAKAO_ACCOUNT = "kakao_account";
    private static final String PROPERTIES = "properties";

    private final SignUpManager signUpManager;

    // 카카오로그인 후 후처리
    @Override
    @Transactional
    public OAuth2User loadUser(final OAuth2UserRequest request) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(request);

        final Map<String, Object> properties = getProperties(oAuth2User);
        final Map<String, Object> account = getKakaoAccount(oAuth2User);

        final SignUpCommand command = toSignUpCommand(properties, account, request);
        final Member member = signUpManager.readOrSignUp(command);

        final String accessToken = getAccessToken(request);
        member.updateAccessToken(accessToken);

        return UserPrincipal.of(oAuth2User, member);
    }

    private Map<String, Object> getProperties(OAuth2User oAuth2User) {
        return oAuth2User.getAttribute(PROPERTIES);
    }

    private Map<String, Object> getKakaoAccount(OAuth2User oAuth2User) {
        return oAuth2User.getAttribute(KAKAO_ACCOUNT);
    }

    private SignUpCommand toSignUpCommand(
        final Map<String, Object> properties,
        final Map<String, Object> account,
        final OAuth2UserRequest request
    ) {
        final String email = (String) account.get("email");
        final String name = (String) properties.get("nickname");
        final String profileImage = (String) properties.get("profile_image");
        final String provider = request.getClientRegistration().getClientName();
        return new SignUpCommand(name, profileImage, email, provider);
    }

    private String getAccessToken(OAuth2UserRequest request) {
        return request.getAccessToken().getTokenValue();
    }
}