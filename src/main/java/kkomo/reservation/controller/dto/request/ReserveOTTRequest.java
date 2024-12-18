package kkomo.reservation.controller.dto.request;

import kkomo.reservation.controller.dto.OTTReservationTimeDto;

public record ReserveOTTRequest(
    Long ottId,
    Long profileId,
    OTTReservationTimeDto time
) {
}
