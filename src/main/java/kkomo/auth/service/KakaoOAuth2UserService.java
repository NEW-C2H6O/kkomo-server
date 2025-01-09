package kkomo.auth.service;

import kkomo.auth.UserPrincipal;
import kkomo.member.domain.Member;
import kkomo.member.repository.MemberRepository;
import kkomo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {

    private static final String KAKAO_ACCOUNT = "kakao_account";
    private static final String PROPERTIES = "properties";

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 카카오로그인 후 후처리
    @Override
    @Transactional
    public OAuth2User loadUser(final OAuth2UserRequest request) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(request);

        final Map<String, Object> properties = getProperties(oAuth2User);
        final Map<String, Object> account = getKakaoAccount(oAuth2User);

        final String email = (String) account.get("email");
        final String name = (String) properties.get("nickname");
        final String profileImage = (String) properties.get("profile_image");
        final String provider = request.getClientRegistration().getClientName();
        final String accessToken = request.getAccessToken().getTokenValue();

        final Member member = memberRepository.findByEmail(email)
            .orElseGet(() -> memberService.registerMember(name, profileImage, email, provider));
        member.updateAccessToken(accessToken);

        memberRepository.save(member);

        log.debug("Loading user: {}, Access Token: {}", oAuth2User.getName(), accessToken);

        return UserPrincipal.of(oAuth2User, member);
    }

    private Map<String, Object> getProperties(OAuth2User oAuth2User) {
        return oAuth2User.getAttribute(PROPERTIES);
    }

    private Map<String, Object> getKakaoAccount(OAuth2User oAuth2User) {
        return oAuth2User.getAttribute(KAKAO_ACCOUNT);
    }
}