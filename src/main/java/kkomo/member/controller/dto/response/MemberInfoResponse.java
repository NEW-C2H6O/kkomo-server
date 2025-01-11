package kkomo.member.controller.dto.response;

public record MemberInfoResponse(
    Long memberId,
    String profileImage,
    String name,
    String nameAndTag,
    Boolean isActivated
) {

    public static MemberInfoResponse of(
        Long memberId,
        String profileImage,
        String name,
        String nameAndTag,
        Boolean isActivated
    ) {
        return new MemberInfoResponse(memberId, profileImage, name, nameAndTag, isActivated);
    }
}
