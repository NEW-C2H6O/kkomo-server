package kkomo.reservation.controller;

import kkomo.reservation.controller.dto.request.ReserveOTTRequest;
import kkomo.reservation.domain.OTTReservationTime;
import kkomo.reservation.service.command.ReserveOTTCommand;
import org.springframework.stereotype.Component;

@Component
public class OTTReservationCommandMapper {

    public ReserveOTTCommand mapToCommand(
        final Long memberId,
        final ReserveOTTRequest request
    ) {
        return new ReserveOTTCommand(
            memberId,
            request.ottId(),
            request.profileId(),
            OTTReservationTime.of(
                request.time().start(),
                request.time().end()
            )
        );
    }
}
