package kkomo.ott.repository;

import kkomo.ott.domain.OTT;
import kkomo.reservation.domain.OTTReservationTime;

import java.util.List;

public interface OTTQueryRepository  {

    List<OTT> findAvailableBy(String ottName, OTTReservationTime time);
}
