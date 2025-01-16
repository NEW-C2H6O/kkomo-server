package kkomo.auth.domain;

import kkomo.member.domain.Member;
import kkomo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpManager {

    private final MemberRepository memberRepository;

    public Member readOrSignUp(final SignUpCommand command) {
        final String email = command.email();
        return memberRepository.findByEmail(email)
            .orElseGet(() -> signUp(
                command.name(),
                command.profileImage(),
                email,
                command.provider()
            ));
    }

    private Member signUp(
        final String name,
        final String profileImage,
        final String email,
        final String provider
    ) {
        final int tagCount = getTagCount(name);
        final Member member = Member.builder()
            .name(name)
            .tag(tagCount + 1)
            .email(email)
            .profileImage(profileImage)
            .provider(provider)
            .build();
        memberRepository.save(member);
        return member;
    }

    private int getTagCount(final String name) {
        return memberRepository.countByName(name);
    }
}
