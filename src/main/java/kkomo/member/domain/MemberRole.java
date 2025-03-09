package kkomo.member.domain;

public enum MemberRole {
    ROLE_ACTIVATED, ROLE_DEACTIVATED, ROLE_ADMIN;

    public boolean isActivated() {
        return this != ROLE_DEACTIVATED;
    }

    public boolean isAdmin() {
        return this == ROLE_ADMIN;
    }

    public boolean isDeactivated() {
        return this == ROLE_DEACTIVATED;
    }
}
