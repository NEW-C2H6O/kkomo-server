package kkomo.member.controller;

import kkomo.auth.UserPrincipal;
import kkomo.global.ApiResponse;
import kkomo.member.service.MemberCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberCommandController {

    private final MemberCommandService memberCommandService;

    @DeleteMapping("/me")
    public ResponseEntity<ApiSuccessResult<?>> withdrawMember(
        @AuthenticationPrincipal final UserPrincipal principal
    ) {
        final Long memberId = principal.getId();
        memberCommandService.withdrawMember(memberId);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
