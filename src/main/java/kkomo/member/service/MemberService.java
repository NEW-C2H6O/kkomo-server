package kkomo.member.service;

import kkomo.member.domain.Member;
import kkomo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member registerMember(
        String name,
        String profileImage,
        String email,
        String provider
    ) {
        int tagCount = getTagCount(name);
        return Member.builder()
            .name(name)
            .tag(tagCount + 1)
            .email(email)
            .profileImage(profileImage)
            .provider(provider)
            .build();
    }

    private int getTagCount(String name) {
        return memberRepository.countByName(name);
    }
}
