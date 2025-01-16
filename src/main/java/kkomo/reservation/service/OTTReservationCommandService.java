package kkomo.reservation.service;

import kkomo.reservation.domain.*;
import kkomo.reservation.service.command.ReserveOTTCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OTTReservationCommandService {

    private final OTTReservationWriter reservationWriter;
    private final OTTReservationDeleter reservationDeleter;

    @Transactional
    public Long reserve(final ReserveOTTCommand command) {
        final Long memberId = command.memberId();
        final Long ottId = command.ottId();
        final Long profileId = command.profileId();
        final OTTReservationTime time = command.time();

        final OTTReservation reservation = reservationWriter.write(memberId, ottId, profileId, time);

        log.debug("예약이 완료됐습니다. [reservationId: {}, memberId: {}]", reservation.getId(), memberId);

        return reservation.getId();
    }

    @Transactional
    public void cancel(final Long memberId, final Long reservationId) {
        reservationDeleter.delete(memberId, reservationId);
        log.debug("예약이 취소됐습니다. [reservationId: {}, memberId: {}]", reservationId, memberId);
    }
}
