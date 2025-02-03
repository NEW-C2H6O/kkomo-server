package kkomo.reservation.domain;

import kkomo.ott.domain.OTTIdAndProfileIds;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
public class OTTReservationFilter {

    private final List<OTTIdAndProfileIds> otts;
    private final boolean mine;
    private final boolean upcoming;
    private final LocalDate date;

    public OTTReservationFilter(
        final List<String> ott,
        final String mine,
        final String upcoming,
        final LocalDate date
    ) {
        this.otts = Optional.ofNullable(ott)
            .orElseGet(List::of)
            .stream()
            .map(OTTIdAndProfileIds::from)
            .toList();
        this.mine = Boolean.parseBoolean(mine);
        this.upcoming = Boolean.parseBoolean(upcoming);
        this.date = Optional.ofNullable(date)
            .orElseGet(LocalDate::now);
    }
}
