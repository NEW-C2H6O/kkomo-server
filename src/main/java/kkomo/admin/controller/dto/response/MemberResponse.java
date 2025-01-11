package kkomo.admin.controller.dto.response;

import kkomo.member.domain.Member;
import kkomo.member.domain.MemberRole;

public record MemberResponse(
    Long memberId,
    String profileImage,
    String name,
    String nameAndTag,
    MemberRole role
) {

    public static MemberResponse from(final Member member) {
        return new MemberResponse(
            member.getId(),
            member.getProfileImage(),
            member.getName(),
            member.getNameAndTag(),
            member.getRole()
        );
    }
}
