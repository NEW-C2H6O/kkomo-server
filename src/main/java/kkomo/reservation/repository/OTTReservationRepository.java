package kkomo.reservation.repository;

import kkomo.reservation.domain.OTTReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OTTReservationRepository extends JpaRepository<OTTReservation, Long> {

    @Query(value = "SELECT r.ott_id " +
        "FROM ott_reservation r " +
        "WHERE r.ott_id = :ottId " +
        "AND r.profile_id = :profileId " +
        "AND r.start_time >= :start " +
        "AND r.end_time <= :end " +
        "FOR UPDATE", nativeQuery = true)
    List<Long> lockByOttIdAndProfileIdAndTimeBetween(
        Long ottId,
        Long profileId,
        LocalDateTime start,
        LocalDateTime end
    );
}
