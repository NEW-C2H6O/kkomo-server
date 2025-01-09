package kkomo.member.service;

import kkomo.member.domain.Member;
import kkomo.member.domain.MemberDeleter;
import kkomo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberDeleter memberDeleter;

    public Member registerMember(
        final String name,
        final String profileImage,
        final String email,
        final String provider
    ) {
        final int tagCount = getTagCount(name);
        return Member.builder()
            .name(name)
            .tag(tagCount + 1)
            .email(email)
            .profileImage(profileImage)
            .provider(provider)
            .build();
    }

    @Transactional
    public void deleteMember(final Long memberId) {
        memberDeleter.delete(memberId);
    }

    private int getTagCount(final String name) {
        return memberRepository.countByName(name);
    }
}
