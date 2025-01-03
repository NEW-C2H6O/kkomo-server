package kkomo.admin.controller.dto.response;

import kkomo.member.domain.Member;
import kkomo.member.domain.MemberRole;

public record MemberResponseWithRole(
    Long memberId,
    String profileImage,
    String name,
    String nameAndTag,
    MemberRole role
) {

    public static MemberResponseWithRole from(final Member member) {
        return new MemberResponseWithRole(
            member.getId(),
            member.getProfileImage(),
            member.getName(),
            member.getNameAndTag(),
            member.getRole()
        );
    }
}
