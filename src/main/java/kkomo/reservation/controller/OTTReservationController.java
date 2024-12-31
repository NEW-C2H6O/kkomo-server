package kkomo.reservation.controller;

import kkomo.global.ApiResponse;
import kkomo.reservation.controller.dto.request.ReserveOTTRequest;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import kkomo.reservation.domain.OTTReservation;
import kkomo.reservation.service.OTTReservationService;
import kkomo.reservation.service.command.ReserveOTTCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class OTTReservationController {

    private final OTTReservationCommandMapper mapper;
    private final OTTReservationService ottReservationService;

    @PostMapping
    public ResponseEntity<ApiSuccessResult<?>> reserveOTT(
        @RequestBody final ReserveOTTRequest request
    ) {
        // TODO: 세션을 통해 memberId를 가져오는 로직 추가 구현 필요
        final Long memberId = 1L;
        final ReserveOTTCommand command = mapper.mapToCommand(memberId, request);
        ottReservationService.reserve(command);
        return ApiResponse.success(HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiSuccessResult<List<GetOTTReservationResponse>>> getMyOTTReservation() {
        // TODO: 세션을 통해 memberId를 가져오는 로직 추가 구현 필요
        final Long memberId = 1L;
        final List<OTTReservation> result = ottReservationService.readMyBy(memberId);
        final List<GetOTTReservationResponse> response = result.stream()
            .map(GetOTTReservationResponse::from)
            .toList();
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
