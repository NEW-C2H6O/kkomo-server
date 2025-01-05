package kkomo.member.service;

import kkomo.member.repository.MemberRepository;
import kkomo.reservation.repository.OTTReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDeleter {

    private final OTTReservationRepository ottReservationRepository;
    private final MemberRepository memberRepository;

    public void delete(final Long memberId) {
        ottReservationRepository.deleteByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }
}
