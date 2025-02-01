package kkomo.ott.repository;

import kkomo.ott.domain.OTT;
import kkomo.ott.domain.OTTIdAndProfileIds;
import kkomo.reservation.domain.OTTReservationTime;

import java.util.List;

public interface OTTQueryRepository  {

    List<OTT> findAvailableBy(List<OTTIdAndProfileIds> otts, OTTReservationTime time);
}
