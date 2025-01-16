package kkomo.reservation.domain;

import kkomo.global.support.Cursor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OTTReservationCursor extends Cursor {

    private final LocalDateTime createdAt;
    private final Long ottId;
    private final String member;

    @Builder
    public OTTReservationCursor(
        final Long reservationId,
        final LocalDateTime createdAt,
        final Long ottId,
        final String member
    ) {
        super(reservationId);
        this.createdAt = createdAt;
        this.ottId = ottId;
        this.member = member;
    }
}
