package kkomo.auth.controller;

import kkomo.auth.UserPrincipal;
import kkomo.auth.controller.dto.request.ActivateMemberRequest;
import kkomo.auth.service.AuthService;
import kkomo.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public RedirectView login() {
        return new RedirectView(authService.getLoginUrl());
    }

    @PostMapping("/activate")
    public ResponseEntity<ApiSuccessResult<?>> activateMember(
        @AuthenticationPrincipal final UserPrincipal principal,
        @RequestBody final ActivateMemberRequest request
    ) {
        final Long memberId = principal.getId();
        final String code = request.code();
        authService.activeMember(memberId, code);
        return ApiResponse.success(HttpStatus.OK);
    }
}
