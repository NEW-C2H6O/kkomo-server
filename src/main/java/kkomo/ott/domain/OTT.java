package kkomo.ott.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ott")
public class OTT {

    @Id
    @Column(name = "ott_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String name;

    @OneToMany(mappedBy = "ott")
    private List<OTTProfile> profiles = new ArrayList<>();

    public OTTProfile getProfileBy(Long profileId) {
        return profiles.stream()
            .filter(profile -> profile.getId().equals(profileId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필입니다."));
    }
}
