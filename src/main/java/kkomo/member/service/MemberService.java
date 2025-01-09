package kkomo.member.service;

import kkomo.member.controller.response.MemberResponse;
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

    public MemberResponse getMemberInfo(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 존재하지 않습니다."));
        String nameAndTag = member.getName() + "#" + member.getTag();
        return MemberResponse.of(
                memberId,
                member.getProfileImage(),
                member.getName(),
                nameAndTag,
                member.isActivated()
        );
    }
    private int getTagCount(String name) {
        return memberRepository.countByName(name);
    }
}
