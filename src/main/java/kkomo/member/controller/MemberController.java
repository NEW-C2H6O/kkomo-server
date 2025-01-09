package kkomo.member.controller;

import kkomo.auth.UserPrincipal;
import kkomo.global.ApiResponse;
import kkomo.member.controller.response.MemberResponse;
import kkomo.member.service.MemberQueryService;
import kkomo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    @GetMapping("/me")
    public ResponseEntity<ApiSuccessResult<MemberResponse>> loadInfo(
        @AuthenticationPrincipal final UserPrincipal principal
    ) {
        final MemberResponse response = memberQueryService.getMemberInfo(principal.getId());
        return ApiResponse.success(HttpStatus.OK, response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiSuccessResult<?>> deleteInfo(
        @AuthenticationPrincipal final UserPrincipal principal
    ) {
        memberService.deleteMember(principal.getId());
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
