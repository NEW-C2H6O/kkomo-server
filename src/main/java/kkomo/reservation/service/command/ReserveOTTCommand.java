package kkomo.reservation.service.command;

import kkomo.reservation.domain.OTTReservationTime;

public record ReserveOTTCommand(
    Long memberId,
    Long ottId,
    Long profileId,
    OTTReservationTime time
) {
}
