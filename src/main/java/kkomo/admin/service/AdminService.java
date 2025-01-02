package kkomo.admin.service;

import kkomo.admin.domain.ActiveCode;
import kkomo.admin.repository.ActiveCodeRepository;
import kkomo.auth.UserPrincipal;
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
    private final ActiveCodeRepository activeCodeRepository;

    @Transactional
    public void publishActiveCode(final String codeValue) {
        if (activeCodeRepository.existsByValue(codeValue)) {
            throw new IllegalArgumentException("이미 사용 중인 코드입니다.");
        }
        final ActiveCode activeCode = ActiveCode.of(codeValue);
        activeCodeRepository.save(activeCode);
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
}
