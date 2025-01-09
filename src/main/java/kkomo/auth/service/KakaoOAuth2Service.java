package kkomo.auth.service;

import kkomo.member.domain.OAuth2Unlinker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class KakaoOAuth2Service implements OAuth2LoginLinkProvider, OAuth2Unlinker, OAuth2Logouter {

    private static final String LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";
    private static final String UNLINK_URL = "https://kapi.kakao.com/v1/user/unlink";

    private final String contextPath;

    @Autowired
    public KakaoOAuth2Service(@Value("${server.servlet.context-path}") String contextPath) {
        this.contextPath = contextPath;
    }

    public String getLoginUrl() {
        return contextPath + "/oauth2/authorization/kakao";
    }

    @Async
    @Override
    public void logout(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        final RestTemplate restTemplate = new RestTemplate();

        final String result = restTemplate.exchange(LOGOUT_URL, HttpMethod.POST, entity, String.class).getBody();

        log.info("Logged out user from kakao : {}", result);
    }

    @Async
    @Override
    public void unlink(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        final RestTemplate restTemplate = new RestTemplate();

        final String result = restTemplate.exchange(UNLINK_URL, HttpMethod.POST, entity, String.class).getBody();

        log.info("Unlink user from kakao : {}", result);
    }
}
