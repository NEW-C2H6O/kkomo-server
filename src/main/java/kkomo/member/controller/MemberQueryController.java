package kkomo.member.controller;

import kkomo.auth.UserPrincipal;
import kkomo.global.ApiResponse;
import kkomo.member.controller.dto.response.MemberInfoResponse;
import kkomo.member.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberQueryController {

    private final MemberQueryService memberQueryService;

    @GetMapping("/me")
    public ResponseEntity<ApiSuccessResult<MemberInfoResponse>> getMemberInfo(
        @AuthenticationPrincipal final UserPrincipal principal
    ) {
        final Long memberId = principal.getId();
        final MemberInfoResponse response = memberQueryService.getMemberInfo(memberId);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
