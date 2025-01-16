package kkomo.reservation.domain;

import kkomo.reservation.repository.OTTReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OTTReservationDeleter {

    private final OTTReservationReader reservationReader;
    private final OTTReservationRepository ottReservationRepository;

    public void delete(final Long memberId, final Long reservationId) {
        final OTTReservation reservation = reservationReader.readById(reservationId);
        if (!reservation.isHolder(memberId)) {
            throw new IllegalArgumentException("예약자 본인이 아닙니다.");
        }
        ottReservationRepository.delete(reservation);
    }
}
