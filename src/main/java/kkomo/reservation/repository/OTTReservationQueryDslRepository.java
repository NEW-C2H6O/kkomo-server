package kkomo.reservation.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.QueryDslSupport;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import kkomo.reservation.domain.OTTReservation;
import kkomo.reservation.domain.OTTReservationTime;
import kkomo.reservation.domain.QOTTReservation;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
class OTTReservationQueryDslRepository extends QueryDslSupport implements OTTReservationQueryRepository {

    private static final QOTTReservation reservation = QOTTReservation.oTTReservation;

    private BooleanExpression cursorBooleanExpression(final CursorPageable<? extends Cursor> pageable) {
        if (!pageable.hasCursor()) {
            return null;
        }
        final Long id = pageable.getCursor().getId();
        return reservation.id.lt(id);
    }

    private List<GetOTTReservationResponse> fetchContent(
        final Long memberId,
        final CursorPageable<? extends Cursor> pageable
    ) {
        final List<OTTReservation> content = queryFactory.selectFrom(reservation)
            .where(reservation.member.id.eq(memberId), cursorBooleanExpression(pageable))
            .orderBy(reservation.id.desc())
            .limit(pageable.getPageSize() + 1)
            .fetch();
        return content.stream()
            .map(GetOTTReservationResponse::from)
            .toList();
    }

    @Override
    public Slice<GetOTTReservationResponse> findByMemberId(
        final Long memberId,
        final CursorPageable<? extends Cursor> pageable
    ) {
        final List<GetOTTReservationResponse> content = fetchContent(memberId, pageable);
        return paginate(content, pageable);
    }

    @Override
    public List<OTTReservationTime> findByOttIdAndProfileIdAndTimeBetween(
        final Long ottId,
        final Long profileId,
        final LocalDateTime start,
        final LocalDateTime end
    ) {
        return queryFactory.select(reservation.time)
            .from(reservation)
            .where(reservation.ott.id.eq(ottId),
                reservation.profile.id.eq(profileId),
                reservation.time.start.goe(start),
                reservation.time.end.loe(end))
            .fetch();
    }
}
