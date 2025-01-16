package kkomo.admin.service;

import kkomo.admin.domain.ActivityCode;
import kkomo.admin.domain.AdminAssigner;
import kkomo.admin.domain.AdminRemover;
import kkomo.admin.domain.MemberDeactivator;
import kkomo.admin.repository.ActivityCodeRepository;
import kkomo.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCommandService {

    private final SessionRegistry sessionRegistry;
    private final ActivityCodeRepository activityCodeRepository;

    private final AdminAssigner adminAssigner;
    private final AdminRemover adminRemover;
    private final MemberDeactivator memberDeactivator;

    @Transactional
    public void publishActivityCode(final String codeValue) {
        if (activityCodeRepository.existsByValue(codeValue)) {
            throw new IllegalArgumentException("이미 사용 중인 코드입니다.");
        }
        final ActivityCode activityCode = ActivityCode.from(codeValue);
        activityCodeRepository.save(activityCode);
        memberDeactivator.deactivateAll();
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
        adminAssigner.assign(memberId);
    }

    @Transactional
    public void removeAdmin(final Long memberId) {
        adminRemover.remove(memberId);
    }
}
