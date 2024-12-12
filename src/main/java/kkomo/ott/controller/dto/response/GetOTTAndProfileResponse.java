package kkomo.ott.controller.dto.response;

import java.util.List;

public record GetOTTAndProfileResponse(
    Long ottId,
    String name,
    List<ProfileResponse> profiles
) {
}

record ProfileResponse(
    Long profileId,
    String name
) {
}