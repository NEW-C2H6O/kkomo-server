package kkomo.reservation.controller.dto.request;

import kkomo.global.controller.dto.ReservationTimeDto;

public record ReserveOTTRequest(
    Long ottId,
    Long profileId,
    ReservationTimeDto time
) {
}
