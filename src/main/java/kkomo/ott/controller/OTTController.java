package kkomo.ott.controller;

import kkomo.global.ApiResponse;
import kkomo.ott.controller.dto.response.GetOTTAndProfileResponse;
import kkomo.ott.service.OTTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static kkomo.global.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ott")
public class OTTController {

    private final OTTService ottService;

    @GetMapping
    public ResponseEntity<ApiSuccessResult<List<GetOTTAndProfileResponse>>> getOTTAndProfile() {
        final List<GetOTTAndProfileResponse> response = ottService.readAll().stream()
                .map(GetOTTAndProfileResponse::from)
                .toList();
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
