package kkomo.reservation.repository;

import kkomo.global.support.Base64Encoder;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorServiceSupport;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OTTReservationCursorService extends CursorServiceSupport {

    private final Base64Encoder base64Encoder;

    public String serializeCursor(final Slice<GetOTTReservationResponse> slice) {
        final Cursor cursor = provideCursor(
            slice,
            s -> {
                final Long id = slice.getContent()
                    .stream()
                    .reduce((first, second) -> second)
                    .map(GetOTTReservationResponse::reservationId)
                    .orElse(null);
                return Cursor.from(id);
            }
        );
        return base64Encoder.encode(cursor);
    }
}
