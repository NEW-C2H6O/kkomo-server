package kkomo.member.domain;

import kkomo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReader {

    private static final String MEMBER_NOT_FOUND = "해당 회원이 존재하지 않습니다.";
    private final MemberRepository memberRepository;

    public Member readById(final Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException(MEMBER_NOT_FOUND));
    }

    public Member readByEmail(final String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException(MEMBER_NOT_FOUND));
    }
}
