package kkomo.reservation.service;

import kkomo.member.domain.Member;
import kkomo.member.repository.MemberRepository;
import kkomo.ott.domain.OTT;
import kkomo.ott.domain.OTTProfile;
import kkomo.ott.repository.OTTRepository;
import kkomo.reservation.domain.OTTReservation;
import kkomo.reservation.domain.OTTReservationTime;
import kkomo.reservation.repository.OTTReservationRepository;
import kkomo.reservation.service.command.ReserveOTTCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OTTReservationService {

    private final OTTRepository ottRepository;
    private final MemberRepository memberRepository;
    private final OTTReservationRepository ottReservationRepository;

    @Transactional
    public Long reserve(final ReserveOTTCommand command) {
        final Long memberId = command.memberId();
        final Long ottId = command.ottId();
        final Long profileId = command.profileId();
        final OTTReservationTime time = command.time();

        final List<Long> reservations = ottReservationRepository.lockByOttIdAndProfileIdAndTimeBetween(
            ottId,
            profileId,
            time.getStart(),
            time.getEnd()
        );

        if (!reservations.isEmpty()) {
            throw new IllegalArgumentException("해당 시간대에 이미 예약된 내역이 있습니다.");
        }

        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        final OTT ott = ottRepository.findById(ottId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 OTT입니다."));
        final OTTProfile profile = ott.getProfileBy(profileId);
        final OTTReservation reservation = OTTReservation.builder()
            .member(member)
            .ott(ott)
            .profile(profile)
            .time(time)
            .build();

        ottReservationRepository.save(reservation);

        return reservation.getId();
    }

    @Transactional
    public void cancel(final Long reservationId, final Long memberId) {
        final OTTReservation reservation = ottReservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 내역입니다."));
        if (!reservation.isHolder(memberId)) {
            throw new IllegalArgumentException("예약자 본인이 아닙니다.");
        }
        ottReservationRepository.delete(reservation);
        log.info("예약이 취소되었습니다. [reservationId: {}, memberId: {}]", reservationId, memberId);
    }
}
