package kkomo.reservation.controller.dto.response;

public record ReserveOTTResponse(
    Long reservationId
) {

    public static ReserveOTTResponse of(final Long reservationId) {
        return new ReserveOTTResponse(reservationId);
    }
}
