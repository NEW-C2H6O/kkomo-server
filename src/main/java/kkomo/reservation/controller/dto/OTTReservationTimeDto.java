package kkomo.reservation.controller.dto;

import kkomo.reservation.domain.OTTReservationTime;

import java.time.LocalDateTime;

public record OTTReservationTimeDto(
    LocalDateTime start,
    LocalDateTime end
) {

    public static OTTReservationTimeDto from(OTTReservationTime time) {
        return new OTTReservationTimeDto(
            time.getStart(),
            time.getEnd()
        );
    }
}
