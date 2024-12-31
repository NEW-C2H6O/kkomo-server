package kkomo.ott.controller.dto.response;

import kkomo.ott.domain.OTT;

import java.util.List;

public record GetOTTAndProfileResponse(
    Long ottId,
    String name,
    List<OTTProfileResponse> profiles
) {

    public static GetOTTAndProfileResponse from(OTT ott) {
        return new GetOTTAndProfileResponse(
            ott.getId(),
            ott.getName(),
            ott.getProfiles().stream()
                .map(OTTProfileResponse::from)
                .toList()
        );
    }
}
