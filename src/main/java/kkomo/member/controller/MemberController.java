package kkomo.member.controller;

import kkomo.auth.UserPrincipal;
import kkomo.global.ApiResponse;
import kkomo.member.controller.response.MemberResponse;
import kkomo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse.ApiSuccessResult<MemberResponse>> loadInfo(
            @AuthenticationPrincipal UserPrincipal principal) {
        log.info("loadInfo {}", principal.getName());
        return ApiResponse.success(HttpStatus.OK, memberService.getMemberInfo(principal.getId()));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse.ApiSuccessResult<String>> deleteInfo(@AuthenticationPrincipal UserPrincipal principal) {
        memberService.deleteMember(principal.getId());
        return ApiResponse.success(HttpStatus.NO_CONTENT, "성공적으로 탈퇴하였습니다.");
    }
}
