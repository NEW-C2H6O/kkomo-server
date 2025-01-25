package kkomo.reservation.repository;

import kkomo.global.support.Base64Encoder;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import kkomo.reservation.domain.OTTReservationCursor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OTTReservationCursorService {

    private final Base64Encoder base64Encoder;

    public String serializeCursor(
        final Slice<GetOTTReservationResponse> slice,
        final Sort sort
    ) {
        final List<GetOTTReservationResponse> content = slice.getContent();

        if (content.isEmpty()) {
            return null;
        }

        final Long id = content.getLast().reservationId();
        final OTTReservationCursor.OTTReservationCursorBuilder builder = OTTReservationCursor.builder()
            .reservationId(id);
        sort.stream()
            .findFirst()
            .map(Sort.Order::getProperty)
            .ifPresent(property -> {
                switch (property) {
                    case "member" -> {
                        final String member = content.getLast().member();
                        builder.member(member);
                    }
                    case "ottId" -> {
                        final Long ottId = content.getLast().ott().ottId();
                        builder.ottId(ottId);
                    }
                    case "createdAt" -> {
                        final LocalDateTime createdAt = content.getLast().createdAt();
                        builder.createdAt(createdAt);
                    }
                };
            });
        final OTTReservationCursor cursor = builder.build();

        return base64Encoder.encode(cursor);
    }
}
