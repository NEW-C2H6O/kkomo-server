package kkomo.member.domain;

import kkomo.member.repository.MemberRepository;
import kkomo.reservation.repository.OTTReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDeleter {

    private final OTTReservationRepository ottReservationRepository;
    private final MemberRepository memberRepository;

    private final MemberReader memberReader;

    private final OAuth2Unlinker oAuth2Unlinker;

    public void delete(final Long memberId) {
        final Member member = memberReader.readById(memberId);
        ottReservationRepository.deleteByMemberId(memberId);
        memberRepository.delete(member);
        oAuth2Unlinker.unlink(member.getAccessToken());
    }
}
