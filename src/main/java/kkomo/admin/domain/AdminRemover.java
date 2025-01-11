package kkomo.admin.domain;

import kkomo.member.domain.Member;
import kkomo.member.domain.MemberReader;
import kkomo.member.domain.MemberRole;
import kkomo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRemover {

    private final MemberRepository memberRepository;
    private final MemberReader memberReader;

    public void remove(final Long memberId) {
        final int count = memberRepository.countByRole(MemberRole.ROLE_ADMIN);
        if (count <= 1) {
            throw new IllegalArgumentException("관리자는 최소 한 명 이상 존재해야 합니다.");
        }
        final Member member = memberReader.readById(memberId);
        if (!member.isAdmin()) {
            throw new IllegalArgumentException("관리자가 아닌 회원입니다.");
        }
        member.removeAdmin();
    }
}
