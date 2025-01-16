package kkomo.reservation.domain;

import kkomo.reservation.repository.OTTReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OTTReservationReader {

    private static final String OTT_NOT_FOUND = "해당 OTT 예약 내역이 존재하지 않습니다.";
    private final OTTReservationRepository reservationRepository;

    public OTTReservation readById(final Long reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException(OTT_NOT_FOUND));
    }
}
