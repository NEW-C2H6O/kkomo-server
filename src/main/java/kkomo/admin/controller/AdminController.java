package kkomo.admin.controller;

import kkomo.admin.controller.dto.request.PublishActiveCodeRequest;
import kkomo.admin.service.AdminService;
import kkomo.global.ApiResponse;
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
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/active-code")
    public ResponseEntity<ApiSuccessResult<?>> publishActiveCode(
        @RequestBody final PublishActiveCodeRequest request
    ) {
        final String codeValue = request.code();
        adminService.publishActiveCode(codeValue);
        return ApiResponse.success(HttpStatus.OK);
    }
}