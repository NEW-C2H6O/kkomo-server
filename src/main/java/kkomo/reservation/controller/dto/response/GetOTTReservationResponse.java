package kkomo.reservation.controller.dto.response;

import kkomo.reservation.controller.dto.OTTReservationTimeDto;

public record GetOTTReservationResponse(
    Long ottId,
    Long profileId,
    OTTReservationTimeDto time
) {
}
