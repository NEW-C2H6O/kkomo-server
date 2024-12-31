package kkomo.reservation.controller;

import kkomo.global.ApiResponse;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorDefault;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.SliceResponse;
import kkomo.reservation.controller.dto.request.ReserveOTTRequest;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import kkomo.reservation.controller.dto.response.ReserveOTTResponse;
import kkomo.reservation.service.OTTReservationQueryService;
import kkomo.reservation.service.OTTReservationService;
import kkomo.reservation.service.command.ReserveOTTCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class OTTReservationController {

    private final OTTReservationCommandMapper mapper;
    private final OTTReservationService ottReservationService;
    private final OTTReservationQueryService ottReservationQueryService;

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

    @GetMapping("/me")
    public ResponseEntity<ApiSuccessResult<SliceResponse<GetOTTReservationResponse>>> getMyOTTReservation(
        @CursorDefault @PageableDefault final CursorPageable<Cursor> pageable
    ) {
        // TODO: 세션을 통해 memberId를 가져오는 로직 추가 구현 필요
        final Long memberId = 1L;
        final SliceResponse<GetOTTReservationResponse> response = ottReservationQueryService.readMyBy(memberId, pageable);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
