package kkomo.admin.domain;

import kkomo.member.domain.Member;
import kkomo.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAssigner {

    private final MemberReader memberReader;

    public void assign(final Long memberId) {
        final Member member = memberReader.readById(memberId);
        if (member.isAdmin()) {
            throw new IllegalArgumentException("이미 관리자인 회원입니다.");
        }
        if (member.isDeactivated()) {
            throw new IllegalArgumentException("비활성화된 회원입니다.");
        }
        member.assignAdmin();
    }
}
