package kkomo.reservation.controller;

import kkomo.global.ApiResponse;
import kkomo.reservation.controller.dto.request.ReserveOTTRequest;
import kkomo.reservation.controller.dto.response.ReserveOTTResponse;
import kkomo.reservation.service.OTTReservationService;
import kkomo.reservation.service.command.ReserveOTTCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class OTTReservationController {

    private final OTTReservationCommandMapper mapper;
    private final OTTReservationService ottReservationService;

    @PostMapping
    public ResponseEntity<ApiSuccessResult<ReserveOTTResponse>> reserveOTT(
        @RequestBody final ReserveOTTRequest request
    ) {
        // TODO: 세션을 통해 memberId를 가져오는 로직 추가 구현 필요
        final Long memberId = 1L;
        final ReserveOTTCommand command = mapper.mapToCommand(memberId, request);
        final Long reservationId = ottReservationService.reserve(command);
        final ReserveOTTResponse response = ReserveOTTResponse.of(reservationId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
