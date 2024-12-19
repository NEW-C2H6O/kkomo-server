package kkomo.reservation.domain;

import jakarta.persistence.*;
import kkomo.global.domain.BaseEntity;
import kkomo.member.domain.Member;
import kkomo.ott.domain.OTT;
import kkomo.ott.domain.OTTProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ott_reservation")
public class OTTReservation extends BaseEntity {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OTTReservationTime time;

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
