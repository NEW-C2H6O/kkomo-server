package kkomo.reservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kkomo.global.domain.BaseEntity;
import kkomo.member.domain.Member;
import kkomo.ott.domain.OTT;
import kkomo.ott.domain.OTTProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ott_reservation")
public class OTTReservation extends BaseEntity {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_time")
    private LocalDateTime start;

    @NotNull
    @Column(name = "end_time")
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ott_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OTT ott;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OTTProfile profile;
}
