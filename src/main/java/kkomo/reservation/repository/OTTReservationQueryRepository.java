package kkomo.reservation.repository;

import kkomo.global.support.Cursor;
import kkomo.global.support.CursorPageable;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import kkomo.reservation.domain.OTTReservationTime;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public interface OTTReservationQueryRepository {

    Slice<GetOTTReservationResponse> findByMember_Id(Long memberId, CursorPageable<? extends Cursor> pageable);

    List<OTTReservationTime> findByOttIdAndProfileIdAndTimeBetween(
        Long ottId,
        Long profileId,
        LocalDateTime start,
        LocalDateTime end
    );
}
