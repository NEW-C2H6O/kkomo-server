package kkomo.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kkomo.global.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer tag;

    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MemberRole role = MemberRole.ROLE_DEACTIVATED;

    private String profileImage;

    private String provider;

    private String accessToken;

    private String refreshToken;

    @Builder
    public Member(String name, int tag, String email, String profileImage, String provider) {
        this.name = name;
        this.tag = tag;
        this.email = email;
        this.profileImage = profileImage;
        this.provider = provider;
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isActivated() {
        return role.isActivated();
    }

    public void activate() {
        this.role = MemberRole.ROLE_ACTIVATED;
    }
}
