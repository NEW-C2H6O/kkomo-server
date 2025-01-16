package kkomo.admin.controller;

import kkomo.admin.controller.dto.response.MemberResponse;
import kkomo.admin.service.AdminQueryService;
import kkomo.global.ApiResponse;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorDefault;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminQueryController {

    private final AdminQueryService adminQueryService;

    @GetMapping
    public ResponseEntity<ApiSuccessResult<SliceResponse<MemberResponse>>> getAdmins(
        @CursorDefault @PageableDefault final CursorPageable<Cursor> pageable
    ) {
        final SliceResponse<MemberResponse> response = adminQueryService.readAdminsBy(pageable);
        return ApiResponse.success(HttpStatus.OK, response);
    }

    @GetMapping("/members")
    public ResponseEntity<ApiSuccessResult<SliceResponse<MemberResponse>>> getMembers(
        @CursorDefault @PageableDefault final CursorPageable<Cursor> pageable
    ) {
        final SliceResponse<MemberResponse> response = adminQueryService.readMembersBy(pageable);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
