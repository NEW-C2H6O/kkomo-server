package kkomo.ott.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ott")
public class OTT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ottId;

    @Column
    @NotNull
    private String name;
}

