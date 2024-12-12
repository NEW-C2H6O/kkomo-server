package kkomo.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kkomo.global.domain.BaseEntity;
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

    private String profileImage;

    private String provider;

    private String accessToken;

    private String refreshToken;
}
