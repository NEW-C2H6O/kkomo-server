package kkomo.auth.controller;

import kkomo.auth.UserPrincipal;
import kkomo.auth.controller.dto.request.ActivateMemberRequest;
import kkomo.auth.service.AuthService;
import kkomo.auth.service.OAuth2LoginLinkProvider;
import kkomo.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final OAuth2LoginLinkProvider oAuth2LoginLinkProvider;

    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView(oAuth2LoginLinkProvider.getLoginUrl());
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
