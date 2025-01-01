package kkomo.ott.controller.dto.response;

import kkomo.ott.domain.OTTProfile;

public record OTTProfileResponse(
    Long profileId,
    String name
) {

    public static OTTProfileResponse from(final OTTProfile profile) {
        return new OTTProfileResponse(profile.getId(), profile.getName());
    }
}
