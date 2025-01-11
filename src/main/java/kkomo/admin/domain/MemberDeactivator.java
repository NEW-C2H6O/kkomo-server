package kkomo.admin.domain;

import kkomo.member.domain.MemberRole;
import kkomo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDeactivator {

    private final MemberRepository memberRepository;

    public void deactivateAll() {
        memberRepository.updateRole(MemberRole.ROLE_ACTIVATED, MemberRole.ROLE_DEACTIVATED);
    }
}
