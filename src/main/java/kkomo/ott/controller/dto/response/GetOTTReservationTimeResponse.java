package kkomo.ott.controller.dto.response;

import kkomo.global.controller.dto.ReservationTimeDto;

import java.util.List;

public record GetOTTReservationTimeResponse(
    List<ReservationTimeDto> times
) {
}