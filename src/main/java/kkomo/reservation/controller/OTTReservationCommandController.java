package kkomo.reservation.controller;

import kkomo.auth.UserPrincipal;
import kkomo.global.ApiResponse;
import kkomo.reservation.controller.dto.request.ReserveOTTRequest;
import kkomo.reservation.controller.dto.response.ReserveOTTResponse;
import kkomo.reservation.service.OTTReservationCommandService;
import kkomo.reservation.service.command.ReserveOTTCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class OTTReservationCommandController {

    private final OTTReservationCommandMapper mapper;
    private final OTTReservationCommandService ottReservationService;

    @PostMapping
    public ResponseEntity<ApiSuccessResult<ReserveOTTResponse>> reserveOTT(
        @RequestBody final ReserveOTTRequest request,
        @AuthenticationPrincipal final UserPrincipal principal
    ) {
        final Long memberId = principal.getId();
        final ReserveOTTCommand command = mapper.mapToCommand(memberId, request);
        final Long reservationId = ottReservationService.reserve(command);
        final ReserveOTTResponse response = ReserveOTTResponse.of(reservationId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ApiSuccessResult<?>> cancelOTT(
        @PathVariable final Long reservationId,
        @AuthenticationPrincipal final UserPrincipal principal
    ) {
        final Long memberId = principal.getId();
        ottReservationService.cancel(memberId, reservationId);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
