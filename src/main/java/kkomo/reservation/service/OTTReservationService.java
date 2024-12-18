package kkomo.reservation.service;

import kkomo.reservation.domain.OTTReservationTime;
import kkomo.reservation.repository.OTTReservationRepository;
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
}
