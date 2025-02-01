package kkomo.ott.controller;

import kkomo.global.ApiResponse;
import kkomo.ott.controller.dto.response.GetOTTAndProfileResponse;
import kkomo.ott.domain.OTTIdAndProfileIds;
import kkomo.ott.service.OTTService;
import kkomo.reservation.controller.dto.OTTReservationTimeDto;
import kkomo.reservation.domain.OTTReservationTime;
import kkomo.reservation.service.OTTReservationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ott")
public class OTTQueryController {

    private final OTTReservationQueryService ottReservationQueryService;
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
        final List<OTTReservationTimeDto> response = ottReservationQueryService.readBy(ottId, profileId, date).stream()
                .map(OTTReservationTimeDto::from)
                .toList();
        return ApiResponse.success(HttpStatus.OK, response);
    }

    @GetMapping("/available")
    public ResponseEntity<ApiSuccessResult<List<GetOTTAndProfileResponse>>> getAvailableOTTAndProfile(
        @RequestParam(required = false) final List<String> ott,
        @RequestParam final LocalDateTime start,
        @RequestParam final LocalDateTime end
    ) {
        final List<OTTIdAndProfileIds> otts = Optional.ofNullable(ott)
            .map(o -> o.stream()
                .map(OTTIdAndProfileIds::from)
                .toList())
            .orElseGet(List::of);
        final OTTReservationTime time = OTTReservationTime.of(start, end);
        final List<GetOTTAndProfileResponse> response = ottService.readAvailable(otts, time).stream()
                .map(GetOTTAndProfileResponse::from)
                .toList();
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
