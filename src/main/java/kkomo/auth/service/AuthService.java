package kkomo.auth.service;

import kkomo.admin.domain.ActivityCode;
import kkomo.admin.repository.ActivityCodeRepository;
import kkomo.member.domain.Member;
import kkomo.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AuthService {

    private final ActivityCodeRepository activityCodeRepository;
    private final MemberRepository memberRepository;
    private final String contextPath;
    private final String LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";
    private final String UNLINK_URL = "https://kapi.kakao.com/v1/user/unlink";


    @Autowired
    public AuthService(
        @Value("${server.servlet.context-path}") String contextPath,
        ActivityCodeRepository activityCodeRepository,
        MemberRepository memberRepository
    ) {
        this.contextPath = contextPath;
        this.activityCodeRepository = activityCodeRepository;
        this.memberRepository = memberRepository;
    }

    public String getLoginUrl() {
        return contextPath + "/oauth2/authorization/kakao";
    }

    @Transactional
    public void activeMember(Long memberId, String activityCode) {
        final ActivityCode code = activityCodeRepository.findTopByOrderByCreatedAtDesc()
            .orElseThrow(() -> new IllegalArgumentException("활동 코드가 존재하지 않습니다."));
        if (!code.isSameValue(activityCode)) {
            throw new IllegalArgumentException("활동 코드가 일치하지 않습니다.");
        }
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        member.activate();
        log.debug("Member {} activated", member.getName());
    }

    public String getAccessToken(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        return member.getAccessToken();
    }

    @Async
    public void logoutFromKakao(String accessToken) {
        String result;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();

            result = restTemplate.exchange(LOGOUT_URL, HttpMethod.POST, entity, String.class).getBody();
            log.info("logged out user from kakao : {}", result);
        } catch (Exception e) {
            // TODO 예외 처리
            e.printStackTrace();
        }
    }

    @Async
    public void unlinkFromKakao(String accessToken) {
        String result;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();

            result = restTemplate.exchange(UNLINK_URL, HttpMethod.POST, entity, String.class).getBody();
            log.info("unlink user from kakao : {}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
