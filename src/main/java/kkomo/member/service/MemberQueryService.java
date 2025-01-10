package kkomo.member.service;

import kkomo.member.controller.dto.response.MemberInfoResponse;
import kkomo.member.domain.Member;
import kkomo.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberReader memberReader;

    public MemberInfoResponse getMemberInfo(final Long memberId) {
        final Member member = memberReader.readById(memberId);
        return MemberInfoResponse.of(
            memberId,
            member.getProfileImage(),
            member.getName(),
            member.getNameAndTag(),
            member.isActivated()
        );
    }
}

