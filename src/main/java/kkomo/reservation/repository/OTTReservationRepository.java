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

    List<OTTReservation> findByMember_Id(Long memberId);
}
