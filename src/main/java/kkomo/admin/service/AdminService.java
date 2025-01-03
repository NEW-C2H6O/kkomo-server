package kkomo.admin.service;

import kkomo.admin.domain.ActivityCode;
import kkomo.admin.repository.ActivityCodeRepository;
import kkomo.auth.UserPrincipal;
import kkomo.member.domain.Member;
import kkomo.member.domain.MemberRole;
import kkomo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final SessionRegistry sessionRegistry;
    private final MemberRepository memberRepository;
    private final ActivityCodeRepository activityCodeRepository;

    @Transactional
    public void publishActivityCode(final String codeValue) {
        if (activityCodeRepository.existsByValue(codeValue)) {
            throw new IllegalArgumentException("이미 사용 중인 코드입니다.");
        }
        final ActivityCode activityCode = ActivityCode.of(codeValue);
        activityCodeRepository.save(activityCode);
        memberRepository.deactivateAllOf(MemberRole.ROLE_ACTIVATED);
        invalidateAllSessions();
    }

    @Async
    public void invalidateAllSessions() {
        sessionRegistry.getAllPrincipals()
            .stream()
            .filter(UserPrincipal.class::isInstance)
            .map(UserPrincipal.class::cast)
            .filter(UserPrincipal::isActivated)
            .forEach(principal ->
                sessionRegistry.getAllSessions(principal, false)
                    .forEach(SessionInformation::expireNow));
    }

    @Transactional
    public void assignAdmin(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        member.assignAdmin();
    }
}
