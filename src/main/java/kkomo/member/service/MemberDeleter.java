package kkomo.member.service;

import kkomo.auth.service.AuthService;
import kkomo.member.domain.Member;
import kkomo.member.repository.MemberRepository;
import kkomo.reservation.repository.OTTReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDeleter {

    private final OTTReservationRepository ottReservationRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    public void delete(final Long memberId) {
        Member member = getMember(memberId);
        ottReservationRepository.deleteByMemberId(memberId);
        memberRepository.deleteById(memberId);
        authService.unlinkFromKakao(member.getAccessToken());
    }

    private Member getMember(final Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 존재하지 않습니다."));
        return member;
    }
}
