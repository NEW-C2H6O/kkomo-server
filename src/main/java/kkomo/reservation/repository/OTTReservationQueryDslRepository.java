package kkomo.reservation.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.QueryDslSupport;
import kkomo.ott.domain.OTTIdAndProfileIds;
import kkomo.reservation.controller.dto.response.GetOTTReservationResponse;
import kkomo.reservation.domain.*;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
class OTTReservationQueryDslRepository extends QueryDslSupport implements OTTReservationQueryRepository {

    private static final QOTTReservation reservation = QOTTReservation.oTTReservation;

    private BooleanExpression cursorBooleanExpression(
        final OTTReservationCursor cursor,
        final Sort.Order order
    ) {
        if (cursor == null) {
            return null;
        }
        if (cursor.getId() == null) {
            throw new IllegalArgumentException("id cursor is null");
        }
        return switch (order.getProperty()) {
            case "member" -> {
                if (cursor.getMember() == null) {
                    throw new IllegalArgumentException("member cursor is null");
                }
                if (order.isAscending()) {
                    yield reservation.member.name.gt(cursor.getMember())
                        .or(reservation.member.name.eq(cursor.getMember())
                            .and(reservation.id.gt(cursor.getId())));
                }
                yield reservation.member.name.lt(cursor.getMember())
                    .or(reservation.member.name.eq(cursor.getMember())
                        .and(reservation.id.lt(cursor.getId())));
            }
            case "ottName" -> {
                if (cursor.getOttName() == null) {
                    throw new IllegalArgumentException("ottName cursor is null");
                }
                if (order.isAscending()) {
                    yield reservation.ott.name.gt(cursor.getOttName())
                        .or(reservation.ott.name.eq(cursor.getOttName())
                            .and(reservation.id.gt(cursor.getId())));
                }
                yield reservation.ott.name.lt(cursor.getOttName())
                    .or(reservation.ott.name.eq(cursor.getOttName())
                        .and(reservation.id.lt(cursor.getId())));
            }
            case "createdAt" -> {
                if (cursor.getCreatedAt() == null) {
                    throw new IllegalArgumentException("createdAt cursor is null");
                }
                if (order.isAscending()) {
                    yield reservation.createdAt.gt(cursor.getCreatedAt())
                        .or(reservation.createdAt.eq(cursor.getCreatedAt())
                            .and(reservation.id.gt(cursor.getId())));
                }
                yield reservation.createdAt.lt(cursor.getCreatedAt())
                    .or(reservation.createdAt.eq(cursor.getCreatedAt())
                        .and(reservation.id.lt(cursor.getId())));
            }
            default -> {
                if (order.isAscending()) {
                    yield reservation.id.gt(cursor.getId());
                }
                yield reservation.id.lt(cursor.getId());
            }
        };
    }

    private OrderSpecifier<?> orderSpecifier(final Sort.Order order) {
        final boolean isAscending = order.isAscending();
        return switch (order.getProperty()) {
            case "member" -> isAscending ? reservation.member.name.asc() : reservation.member.name.desc();
            case "ottName" -> isAscending ? reservation.ott.name.asc() : reservation.ott.name.desc();
            case "createdAt" -> isAscending ? reservation.time.start.asc() : reservation.time.start.desc();
            default -> isAscending ? reservation.id.asc() : reservation.id.desc();
        };
    }

    private BooleanExpression reservationMemberIdEq(final Long memberId) {
        if (memberId == null) {
            return null;
        }
        return reservation.member.id.eq(memberId);
    }

    private BooleanExpression reservationOttIdEq(final Long ottId) {
        if (ottId == null) {
            return null;
        }
        return reservation.ott.id.eq(ottId);
    }

    private BooleanBuilder reservationOttIdEqAndProfileIdsInOr(final List<OTTIdAndProfileIds> otts) {
        final BooleanBuilder builder = new BooleanBuilder();
        otts.stream()
            .map(this::reservationOttIdEqAndProfileIdsIn)
            .forEach(builder::or);
        return builder;
    }

    private BooleanBuilder reservationOttIdEqAndProfileIdsIn(final OTTIdAndProfileIds ottIdAndProfileIds) {
        final BooleanBuilder builder = new BooleanBuilder();
        final Long ottId = ottIdAndProfileIds.ottId();
        final List<Long> profileIds = ottIdAndProfileIds.profileIds();
        builder.and(reservationOttIdEq(ottId));
        if (!profileIds.isEmpty()) {
            builder.and(reservation.profile.id.in(profileIds));
        }
        return builder;
    }

    @Override
    public Slice<GetOTTReservationResponse> findBy(
        final Long memberId,
        final OTTReservationFilter filter,
        final CursorPageable<OTTReservationCursor> pageable
    ) {
        final JPAQuery<OTTReservation> query = queryFactory.selectFrom(reservation);
        if (filter.isMine()) {
            query.where(reservationMemberIdEq(memberId));
        }
        if (filter.isUpcoming()) {
            final LocalDateTime now = LocalDateTime.now();
            query.where(reservation.time.end.goe(now));
        }
        query.where(reservationOttIdEqAndProfileIdsInOr(filter.getOtts()));

        final OTTReservationCursor cursor = pageable.getCursor();
        final Sort.Order order = pageable.getSort()
            .stream()
            .findFirst()
            .orElseGet(() -> Sort.Order.desc("reservationId"));
        final int pageSize = pageable.getPageSize();

        query.where(cursorBooleanExpression(cursor, order))
            .orderBy(orderSpecifier(order))
            .limit(pageSize + 1);

        final List<GetOTTReservationResponse> content = query.fetch()
            .stream()
            .map(GetOTTReservationResponse::from)
            .toList();

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
