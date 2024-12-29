package kkomo.security.service;

import jakarta.servlet.http.HttpSession;
import java.util.Map;
import kkomo.member.domain.Member;
import kkomo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    //카카오로그인 후 후처리
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("Loading user: {}", oAuth2User);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> profile = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) profile.get("email");

        Member existingMember = memberRepository.findByEmail(email)
                .orElseGet(() -> registerMember((Map<String, Object>) attributes.get("properties"), email, userRequest.getClientRegistration().getClientName()));

        log.debug("token {} : " , userRequest.getAccessToken());
        existingMember.updateAccessToken(userRequest.getAccessToken());
        memberRepository.save(existingMember);

        return oAuth2User;
    }

    private Member registerMember(Map<String, Object> attributes, String email, String provider) {
        int tagCount = getTagCount(attributes);
        log.debug("attributes: {}", attributes);
        return Member.builder()
                .name((String) attributes.get("nickname"))
                .tag(tagCount + 1)
                .email(email)
                .profileImage((String) attributes.get("profile_image"))
                .provider(provider)
                .build();
    }

    private int getTagCount(Map<String, Object> attributes) {
        return memberRepository.countByName((String) attributes.get("nickname"));
    }
}
