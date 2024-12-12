package kkomo.reservation.controller.dto.response;

import kkomo.global.controller.dto.ReservationTimeDto;

public record GetOTTReservationResponse(
    Long ottId,
    Long profileId,
    ReservationTimeDto time
) {
}
