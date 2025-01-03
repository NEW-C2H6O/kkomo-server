package kkomo.member.domain;

public enum MemberRole {
    ROLE_ACTIVATED, ROLE_DEACTIVATED, ROLE_ADMIN;

    public boolean isActivated() {
        return this == ROLE_ACTIVATED;
    }
}
