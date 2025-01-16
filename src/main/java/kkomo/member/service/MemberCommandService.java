package kkomo.member.service;

import kkomo.member.domain.MemberDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberDeleter memberDeleter;

    @Transactional
    public void withdrawMember(final Long memberId) {
        memberDeleter.delete(memberId);
    }
}
