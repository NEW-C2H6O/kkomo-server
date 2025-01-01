package kkomo.reservation.service;

import kkomo.global.support.Cursor;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.SliceResponse;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import kkomo.reservation.domain.OTTReservationTime;
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

    public List<OTTReservationTime> readBy(Long ottId, Long profileId, LocalDate date) {
        final LocalDateTime start = date.atStartOfDay();
        final LocalDateTime end = date.atTime(23, 59, 59);

        return queryRepository.findByOttIdAndProfileIdAndTimeBetween(
            ottId,
            profileId,
            start,
            end
        );
    }

    public SliceResponse<GetOTTReservationResponse> readMyBy(
        final Long memberId,
        final CursorPageable<? extends Cursor> pageable
    ) {
        final Slice<GetOTTReservationResponse> response = queryRepository.findByMember_Id(memberId, pageable);
        final String cursor = cursorService.serializeCursor(response);
        return SliceResponse.of(response, cursor);
    }
}
