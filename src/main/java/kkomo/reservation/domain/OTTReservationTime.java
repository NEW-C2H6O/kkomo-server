package kkomo.reservation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OTTReservationTime {

    @NotNull
    @Column(name = "start_time")
    private LocalDateTime start;

    @NotNull
    @Column(name = "end_time")
    private LocalDateTime end;

    public OTTReservationTime(
        final LocalDateTime start,
        final LocalDateTime end
    ) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }
        this.start = start;
        this.end = end;
    }

    public static OTTReservationTime of(
        final LocalDateTime start,
        final LocalDateTime end
    ) {
        return new OTTReservationTime(start, end);
    }
}
