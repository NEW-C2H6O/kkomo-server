package kkomo.member.controller.response;

public record MemberResponse(
    Long memberId,
    String profileImage,
    String name,
    String nameAndTag
) {
}
