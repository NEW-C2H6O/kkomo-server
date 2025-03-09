package kkomo.admin.service;

import kkomo.admin.domain.*;
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
    private final ActivityCodeGenerator activityCodeGenerator;

    @Transactional
    public ActivityCode publishActivityCode() {
        final ActivityCode code = activityCodeGenerator.generate();
        activityCodeRepository.save(code);
        memberDeactivator.deactivateAll();
        invalidateAllSessions();
        return code;
    }

    @Async
    public void invalidateAllSessions() {
        sessionRegistry.getAllPrincipals()
            .stream()
            .filter(UserPrincipal.class::isInstance)
            .map(UserPrincipal.class::cast)
            .filter(principal ->
                    principal.isActivated() && !principal.isAdmin())
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
