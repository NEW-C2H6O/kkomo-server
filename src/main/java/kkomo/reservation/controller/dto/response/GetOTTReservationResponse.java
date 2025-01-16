package kkomo.reservation.controller.dto.response;

import kkomo.ott.controller.dto.response.OTTProfileResponse;
import kkomo.ott.domain.OTT;
import kkomo.ott.domain.OTTProfile;
import kkomo.reservation.controller.dto.OTTReservationTimeDto;
import kkomo.reservation.domain.OTTReservation;

import java.time.LocalDateTime;

public record GetOTTReservationResponse(
    Long reservationId,
    String member,
    OTTResponse ott,
    OTTReservationTimeDto time,
    LocalDateTime createdAt
) {

    public record OTTResponse(
        Long ottId,
        String name,
        OTTProfileResponse profile
    ) {

        static OTTResponse of(final OTT ott, final OTTProfile profile) {
            return new OTTResponse(
                ott.getId(),
                ott.getName(),
                OTTProfileResponse.from(profile)
            );
        }
    }

    public static GetOTTReservationResponse from(final OTTReservation reservation) {
        return new GetOTTReservationResponse(
            reservation.getId(),
            reservation.getMember().getName(),
            OTTResponse.of(reservation.getOtt(), reservation.getProfile()),
            OTTReservationTimeDto.from(reservation.getTime()),
            reservation.getCreatedAt()
        );
    }
}
