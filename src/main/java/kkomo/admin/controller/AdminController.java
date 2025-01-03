package kkomo.admin.controller;

import kkomo.admin.controller.dto.request.AssignAdminRequest;
import kkomo.admin.controller.dto.request.PublishActivityCodeRequest;
import kkomo.admin.controller.dto.response.MemberResponseWithRole;
import kkomo.admin.service.AdminQueryService;
import kkomo.admin.service.AdminService;
import kkomo.global.ApiResponse;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorDefault;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminQueryService adminQueryService;
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ApiSuccessResult<SliceResponse<MemberResponseWithRole>>> getAdmins(
        @CursorDefault @PageableDefault final CursorPageable<Cursor> pageable
    ) {
        final SliceResponse<MemberResponseWithRole> response = adminQueryService.readBy(pageable);
        return ApiResponse.success(HttpStatus.OK, response);
    }

    @PostMapping("/activity-code")
    public ResponseEntity<ApiSuccessResult<?>> publishActivityCode(
        @RequestBody final PublishActivityCodeRequest request
    ) {
        final String codeValue = request.code();
        adminService.publishActivityCode(codeValue);
        return ApiResponse.success(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResult<?>> assignAdmin(
        @RequestBody final AssignAdminRequest request
    ) {
        final Long memberId = request.memberId();
        adminService.assignAdmin(memberId);
        return ApiResponse.success(HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiSuccessResult<?>> removeAdmin(
        @PathVariable final Long memberId
    ) {
        adminService.removeAdmin(memberId);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
