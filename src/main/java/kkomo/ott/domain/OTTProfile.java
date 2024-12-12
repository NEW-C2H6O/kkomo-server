package kkomo.ott.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ott_profile")
public class OTTProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "ott_id")
    private OTT ott;
}

