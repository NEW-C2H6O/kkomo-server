package kkomo.member.controller.response;

public record MemberResponse(
    Long memberId,
    String profileImage,
    String name,
    String nameAndTag,
    Boolean isActivated
) {

    public static MemberResponse of(
        Long memberId,
        String profileImage,
        String name,
        String nameAndTag,
        Boolean isActivated
    ) {
        return new MemberResponse(memberId, profileImage, name, nameAndTag, isActivated);
    }
}
