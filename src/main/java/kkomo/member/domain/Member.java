package kkomo.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kkomo.global.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

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
    private MemberRole role;

    private String profileImage;

    private String provider;

    private String accessToken;

    private String refreshToken;

    @Builder
    public Member(String name, int tag, String email, String profileImage,String provider) {
        this.name = name;
        this.tag = tag;
        this.email = email;
        this.profileImage = profileImage;
        this.provider = provider;
        this.role = MemberRole.ROLE_DEACTIVATED;  //회원 활성화 이후에 권한을 얻을 수 있다
    }

    public void updateAccessToken(OAuth2AccessToken accessToken) {
        this.accessToken = accessToken.getTokenValue();
    }
}
