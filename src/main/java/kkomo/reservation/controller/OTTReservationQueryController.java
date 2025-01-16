package kkomo.reservation.controller;

import kkomo.auth.UserPrincipal;
import kkomo.global.ApiResponse;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorDefault;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.SliceResponse;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import kkomo.reservation.service.OTTReservationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class OTTReservationQueryController {

    private final OTTReservationQueryService queryService;

    @GetMapping("/me")
    public ResponseEntity<ApiSuccessResult<SliceResponse<GetOTTReservationResponse>>> getMyOTTReservation(
        @CursorDefault @PageableDefault final CursorPageable<Cursor> pageable,
        @AuthenticationPrincipal final UserPrincipal principal
    ) {
        final Long memberId = principal.getId();
        final SliceResponse<GetOTTReservationResponse> response = queryService.readMyBy(memberId, pageable);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
