package kkomo.reservation.repository;

import kkomo.reservation.domain.OTTReservation;
import kkomo.reservation.domain.OTTReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OTTReservationRepository extends JpaRepository<OTTReservation, Long> {

    @Query("select r.time from OTTReservation r " +
            "where r.ott.id = :ottId and r.profile.id = :profileId and " +
            "r.time.start >= :start and r.time.end <= :end")
    List<OTTReservationTime> findByOttIdAndProfileIdAndTimeBetween(
        Long ottId,
        Long profileId,
        LocalDateTime start,
        LocalDateTime end
    );
}
