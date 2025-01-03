package kkomo.member.controller.response;

public record MemberResponse(
    Long memberId,
    String profileImage,
    String name,
    String nameAndTag
) {

    public static MemberResponse of(
        Long memberId,
        String profileImage,
        String name,
        String nameAndTag
    ) {
        return new MemberResponse(memberId, profileImage, name, nameAndTag);
    }
}
