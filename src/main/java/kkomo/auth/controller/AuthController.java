package kkomo.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import kkomo.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final HttpServletRequest request;

    @PostMapping("/login")
    public RedirectView login() {
        return new RedirectView("/v1/oauth2/authorization/kakao");
    }
}
