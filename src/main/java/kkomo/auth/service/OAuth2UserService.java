package kkomo.auth.service;

import java.util.Map;
import kkomo.member.domain.Member;
import kkomo.member.repository.MemberRepository;
import kkomo.member.service.MemberService;
import kkomo.auth.UserPrincipal;
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
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;
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

        log.info("Loading user: {}", attributes.get("properties"));
        Member existingMember = memberRepository.findByEmail(email)
                .orElseGet(() -> memberService.registerMember((Map<String, Object>) attributes.get("properties"), email,
                        userRequest.getClientRegistration().getClientName()));

        log.debug("token {} : ", userRequest.getAccessToken());
        existingMember.updateAccessToken(userRequest.getAccessToken().getTokenValue());
        memberRepository.save(existingMember);

        return new UserPrincipal(oAuth2User, existingMember);
    }
}
