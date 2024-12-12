package kkomo.reservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class OTTReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @NotNull
    @Column(name = "start_time")
    private LocalDateTime start;

    @NotNull
    @Column(name = "end_time")
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ott_id")
    private OTT ott;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private OTTProfile profile;
}

