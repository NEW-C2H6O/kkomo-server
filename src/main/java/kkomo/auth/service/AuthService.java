package kkomo.auth.service;

import kkomo.admin.domain.ActivityCode;
import kkomo.admin.repository.ActivityCodeRepository;
import kkomo.member.domain.Member;
import kkomo.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final ActivityCodeRepository activityCodeRepository;
    private final MemberReader memberReader;

    @Transactional
    public void activeMember(final Long memberId, final String activityCode) {
        final ActivityCode code = activityCodeRepository.findTopByOrderByCreatedAtDesc()
            .orElseThrow(() -> new IllegalArgumentException("활동 코드가 존재하지 않습니다."));
        if (!code.isSameValue(activityCode)) {
            throw new IllegalArgumentException("활동 코드가 일치하지 않습니다.");
        }
        final Member member = memberReader.readById(memberId);
        member.activate();
        log.debug("Member {} activated", member.getName());
    }
}
