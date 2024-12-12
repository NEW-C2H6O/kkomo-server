package kkomo.global.controller.dto;

import java.time.LocalDateTime;

public record ReservationTimeDto(
    LocalDateTime start,
    LocalDateTime end
) {
}
