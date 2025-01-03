package kkomo.auth.service;

import kkomo.admin.domain.ActivityCode;
import kkomo.admin.repository.ActivityCodeRepository;
import kkomo.member.domain.Member;
import kkomo.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthService {

    private final ActivityCodeRepository activityCodeRepository;
    private final MemberRepository memberRepository;
    private final String contextPath;

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
}
