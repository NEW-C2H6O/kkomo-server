package kkomo.ott.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import kkomo.global.support.QueryDslSupport;
import kkomo.ott.domain.OTT;
import kkomo.ott.domain.OTTIdAndProfileIds;
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
    public List<OTT> findAvailableBy(
        final List<OTTIdAndProfileIds> otts,
        final OTTReservationTime time
    ) {
        return queryFactory.select(ott)
            .from(ott)
            .join(ott.profiles, profile).fetchJoin()
            .where(ottIdEqAndProfileIdsInOr(otts), notReservedAt(time))
            .fetch();
    }

    private BooleanExpression notReservedAt(final OTTReservationTime time) {
        return profile.id.notIn(
            JPAExpressions.select(reservation.profile.id)
                .from(reservation)
                .where(reservation.time.start.goe(time.getStart())
                    .and(reservation.time.end.loe(time.getEnd())))
        );
    }

    private BooleanBuilder ottIdEqAndProfileIdsInOr(final List<OTTIdAndProfileIds> otts) {
        final BooleanBuilder builder = new BooleanBuilder();
        otts.stream()
            .map(this::ottIdEqAndProfileIds)
            .forEach(builder::or);
        return builder;
    }

    private BooleanBuilder ottIdEqAndProfileIds(final OTTIdAndProfileIds ottIdAndProfileIds) {
        final BooleanBuilder builder = new BooleanBuilder();
        final Long ottId = ottIdAndProfileIds.ottId();
        final List<Long> profileIds = ottIdAndProfileIds.profileIds();
        builder.and(ott.id.eq(ottId));
        if (!profileIds.isEmpty()) {
            builder.and(profile.id.in(profileIds));
        }
        return builder;
    }
}
