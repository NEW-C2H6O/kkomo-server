package kkomo.ott.controller;

import kkomo.global.ApiResponse;
import kkomo.reservation.controller.dto.OTTReservationTimeDto;
import kkomo.ott.controller.dto.response.GetOTTAndProfileResponse;
import kkomo.ott.service.OTTService;
import kkomo.reservation.service.OTTReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ott")
public class OTTController {

    private final OTTReservationService ottReservationService;
    private final OTTService ottService;

    @GetMapping
    public ResponseEntity<ApiSuccessResult<List<GetOTTAndProfileResponse>>> getOTTAndProfile() {
        final List<GetOTTAndProfileResponse> response = ottService.readAll().stream()
                .map(GetOTTAndProfileResponse::from)
                .toList();
        return ApiResponse.success(HttpStatus.OK, response);
    }

    @GetMapping("/{ottId}/{profileId}/reservations")
    public ResponseEntity<ApiSuccessResult<List<OTTReservationTimeDto>>> getOTTReservationTime(
        @PathVariable final Long ottId,
        @PathVariable final Long profileId,
        @RequestParam final LocalDate date
    ) {
        final List<OTTReservationTimeDto> response = ottReservationService.readBy(ottId, profileId, date).stream()
                .map(OTTReservationTimeDto::from)
                .toList();
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
