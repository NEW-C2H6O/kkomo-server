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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OTTReservationService {

    private final OTTRepository ottRepository;
    private final MemberRepository memberRepository;
    private final OTTReservationRepository ottReservationRepository;

    public List<OTTReservationTime> readBy(Long ottId, Long profileId, LocalDate date) {
        final LocalDateTime start = date.atStartOfDay();
        final LocalDateTime end = date.atTime(23, 59, 59);

        return ottReservationRepository.findByOttIdAndProfileIdAndTimeBetween(
            ottId,
            profileId,
            start,
            end
        );
    }

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

    public List<OTTReservation> readMyBy(Long memberId) {
        return ottReservationRepository.findByMember_Id(memberId);
    }
}
