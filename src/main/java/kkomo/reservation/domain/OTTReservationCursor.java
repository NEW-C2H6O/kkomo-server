package kkomo.reservation.domain;

import kkomo.global.support.Cursor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OTTReservationCursor extends Cursor {

    private final LocalDateTime createdAt;
    private final String ottName;
    private final String member;

    @Builder
    public OTTReservationCursor(
        final Long reservationId,
        final LocalDateTime createdAt,
        final String ottName,
        final String member
    ) {
        super(reservationId);
        this.createdAt = createdAt;
        this.ottName = ottName;
        this.member = member;
    }
}
