package kkomo.reservation.service;

import kkomo.global.support.CursorPageable;
import kkomo.global.support.SliceResponse;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import kkomo.reservation.domain.OTTReservationCursor;
import kkomo.reservation.domain.OTTReservationFilter;
import kkomo.reservation.domain.OTTReservationTime;
import kkomo.reservation.repository.OTTReservationCursorService;
import kkomo.reservation.repository.OTTReservationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OTTReservationQueryService {

    private final OTTReservationQueryRepository queryRepository;
    private final OTTReservationCursorService cursorService;

    public List<OTTReservationTime> readBy(
        final Long ottId,
        final Long profileId,
        final LocalDate date
    ) {
        final LocalDateTime start = date.atStartOfDay();
        final LocalDateTime end = date.atTime(23, 59, 59);

        return queryRepository.findByOttIdAndProfileIdAndTimeBetween(
            ottId,
            profileId,
            start,
            end
        );
    }

    public SliceResponse<GetOTTReservationResponse> readBy(
        Long memberId,
        OTTReservationFilter filter,
        CursorPageable<OTTReservationCursor> pageable
    ) {
        final Slice<GetOTTReservationResponse> response = queryRepository.findBy(
            memberId,
            filter,
            pageable
        );
        final String next = cursorService.serializeCursor(response, pageable.getSort());
        return SliceResponse.of(response, next);
    }
}
