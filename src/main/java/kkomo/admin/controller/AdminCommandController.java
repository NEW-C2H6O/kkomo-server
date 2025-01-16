package kkomo.admin.controller;

import kkomo.admin.controller.dto.request.AssignAdminRequest;
import kkomo.admin.controller.dto.request.PublishActivityCodeRequest;
import kkomo.admin.service.AdminCommandService;
import kkomo.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCommandController {

    private final AdminCommandService adminCommandService;

    @PostMapping("/activity-code")
    public ResponseEntity<ApiSuccessResult<?>> publishActivityCode(
        @RequestBody final PublishActivityCodeRequest request
    ) {
        final String codeValue = request.code();
        adminCommandService.publishActivityCode(codeValue);
        return ApiResponse.success(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResult<?>> assignAdmin(
        @RequestBody final AssignAdminRequest request
    ) {
        final Long memberId = request.memberId();
        adminCommandService.assignAdmin(memberId);
        return ApiResponse.success(HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiSuccessResult<?>> removeAdmin(
        @PathVariable final Long memberId
    ) {
        adminCommandService.removeAdmin(memberId);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
