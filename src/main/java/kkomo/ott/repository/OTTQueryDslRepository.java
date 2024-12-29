package kkomo.ott.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import kkomo.global.support.QueryDslSupport;
import kkomo.ott.domain.OTT;
import kkomo.ott.domain.QOTT;
import kkomo.ott.domain.QOTTProfile;
import kkomo.reservation.domain.OTTReservationTime;
import kkomo.reservation.domain.QOTTReservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class OTTQueryDslRepository extends QueryDslSupport implements OTTQueryRepository {

    private static final QOTT ott = QOTT.oTT;
    private static final QOTTProfile profile = QOTTProfile.oTTProfile;
    private static final QOTTReservation reservation = QOTTReservation.oTTReservation;

    @Override
    public List<OTT> findAvailableBy(final Long ottId, final OTTReservationTime time) {
        return queryFactory.select(ott)
                .from(ott)
                .join(ott.profiles, profile).fetchJoin()
                .leftJoin(reservation).on(reservation.ott.eq(ott)
                    .and(reservation.profile.eq(profile))
                    .and(reservation.time.start.goe(time.getStart()))
                    .and(reservation.time.end.loe(time.getEnd())))
                .where(ottIdEq(ottId), reservation.id.isNull())
                .fetch();
    }

    private BooleanExpression ottIdEq(final Long ottId) {
        return ottId == null ? null : ott.id.eq(ottId);
    }
}
