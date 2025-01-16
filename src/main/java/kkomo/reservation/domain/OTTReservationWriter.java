package kkomo.reservation.domain;

import kkomo.member.domain.Member;
import kkomo.member.domain.MemberReader;
import kkomo.ott.domain.OTT;
import kkomo.ott.domain.OTTProfile;
import kkomo.ott.domain.OTTReader;
import kkomo.reservation.repository.OTTReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OTTReservationWriter {

    private final MemberReader memberReader;
    private final OTTReader ottReader;

    private final OTTReservationRepository reservationRepository;

    public OTTReservation write(
        final Long memberId,
        final Long ottId,
        final Long profileId,
        final OTTReservationTime time
    ) {
        final List<Long> reservations = reservationRepository.lockByOttIdAndProfileIdAndTimeBetween(
            ottId,
            profileId,
            time.getStart(),
            time.getEnd()
        );

        if (!reservations.isEmpty()) {
            throw new IllegalArgumentException("해당 시간대에 이미 예약된 내역이 있습니다.");
        }

        final Member member = memberReader.readById(memberId);
        final OTT ott = ottReader.readById(ottId);
        final OTTProfile profile = ott.getProfileBy(profileId);

        final OTTReservation reservation = OTTReservation.builder()
            .member(member)
            .ott(ott)
            .profile(profile)
            .time(time)
            .build();

        reservationRepository.save(reservation);

        return reservation;
    }
}
