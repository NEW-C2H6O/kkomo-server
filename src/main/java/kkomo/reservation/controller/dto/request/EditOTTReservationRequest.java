package kkomo.reservation.controller.dto.request;

import kkomo.global.controller.dto.ReservationTimeDto;

public record EditOTTReservationRequest(
    Long ottId,
    Long profileId,
    ReservationTimeDto time
) {
}
