package kkomo.ott.service;

import kkomo.ott.domain.OTT;
import kkomo.ott.domain.OTTIdAndProfileIds;
import kkomo.ott.repository.OTTQueryRepository;
import kkomo.ott.repository.OTTRepository;
import kkomo.reservation.domain.OTTReservationTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OTTService {

    private final OTTRepository ottRepository;
    private final OTTQueryRepository ottQueryRepository;

    public List<OTT> readAll() {
        return ottRepository.findAll();
    }

    public List<OTT> readAvailable(
        final List<OTTIdAndProfileIds> otts,
        final OTTReservationTime time
    ) {
        if (time.getStart().isAfter(time.getEnd())) {
            throw new IllegalArgumentException("올바르지 않은 예약 시간입니다.");
        }
        return ottQueryRepository.findAvailableBy(otts, time);
    }
}
