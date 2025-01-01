package kkomo.reservation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kkomo.global.support.Cursor;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
class OTTReservationCursorService {

    private final ObjectMapper objectMapper;

    public String serializeCursor(final Slice<GetOTTReservationResponse> slice) {
        if (!slice.hasNext()) {
            return null;
        }
        final Long id = slice.getContent()
            .stream()
            .reduce((first, second) -> second)
            .map(GetOTTReservationResponse::reservationId)
            .orElse(null);
        if (id == null) {
            return null;
        }
        final Cursor cursor = new Cursor(id);
        try {
            final String cursorJson = objectMapper.writeValueAsString(cursor);
            return Base64.getEncoder().encodeToString(cursorJson.getBytes());
        } catch (final JsonProcessingException exception) {
            throw new IllegalArgumentException("Failed to serialize cursor:", exception);
        }
    }
}
