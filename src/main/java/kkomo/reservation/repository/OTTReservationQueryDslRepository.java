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

import java.time.LocalDate;
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

        final Long reservationId = cursor.getId();

        if (reservationId == null) {
            throw new IllegalArgumentException("id cursor is null");
        }

        return switch (order.getProperty()) {
            case OTTReservationSortOrder.MEMBER -> {
                final String member = cursor.getMember();

                if (member == null) {
                    throw new IllegalArgumentException("member cursor is null");
                }

                if (order.isAscending()) {
                    yield reservation.member.name.gt(member)
                        .or(reservation.member.name.eq(member)
                            .and(reservation.id.gt(reservationId)));
                }

                yield reservation.member.name.lt(member)
                    .or(reservation.member.name.eq(member)
                        .and(reservation.id.gt(reservationId)));
            }
            case OTTReservationSortOrder.OTT_NAME -> {
                final String ottName = cursor.getOttName();

                if (ottName == null) {
                    throw new IllegalArgumentException("ottName cursor is null");
                }

                if (order.isAscending()) {
                    yield reservation.ott.name.gt(ottName)
                        .or(reservation.ott.name.eq(ottName)
                            .and(reservation.id.gt(reservationId)));
                }

                yield reservation.ott.name.lt(ottName)
                    .or(reservation.ott.name.eq(ottName)
                        .and(reservation.id.gt(reservationId)));
            }
            case OTTReservationSortOrder.START_TIME -> {
                final LocalDateTime startTime = cursor.getStartTime();

                if (startTime == null) {
                    throw new IllegalArgumentException("start time cursor is null");
                }

                if (order.isAscending()) {
                    yield reservation.time.start.gt(startTime)
                        .or(reservation.time.start.eq(startTime)
                            .and(reservation.id.gt(reservationId)));
                }

                yield reservation.time.start.gt(startTime)
                    .or(reservation.time.start.eq(startTime)
                        .and(reservation.id.gt(reservationId)));
            }
            default -> {
                if (order.isAscending()) {
                    yield reservation.id.gt(reservationId);
                }

                yield reservation.id.lt(reservationId);
            }
        };
    }

    private OrderSpecifier<?>[] orderSpecifiers(final Sort.Order order) {
        final OrderSpecifier<?>[] specifiers = new OrderSpecifier[2];
        specifiers[0] = orderSpecifier(order);
        if (specifiers[0].getTarget() == reservation.id) {
            return new OrderSpecifier[] { specifiers[0] };
        }
        specifiers[1] = reservation.id.asc();
        return specifiers;
    }

    private OrderSpecifier<?> orderSpecifier(final Sort.Order order) {
        final boolean isAscending = order.isAscending();
        return switch (order.getProperty()) {
            case OTTReservationSortOrder.MEMBER -> isAscending ? reservation.member.name.asc() : reservation.member.name.desc();
            case OTTReservationSortOrder.OTT_NAME -> isAscending ? reservation.ott.name.asc() : reservation.ott.name.desc();
            case OTTReservationSortOrder.START_TIME -> isAscending ? reservation.time.start.asc() : reservation.time.start.desc();
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

    private BooleanExpression reservationTimeEq(final LocalDate date) {
        final LocalDateTime dateTime = date.atStartOfDay();
        final LocalDateTime nextDateTime = date.plusDays(1).atStartOfDay();
        return reservation.time.start.goe(dateTime)
            .and(reservation.time.end.lt(nextDateTime));
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
        query.where(
            reservationTimeEq(filter.getDate()),
            reservationOttIdEqAndProfileIdsInOr(filter.getOtts())
        );

        final OTTReservationCursor cursor = pageable.getCursor();
        final Sort.Order order = pageable.getSort()
            .stream()
            .findFirst()
            .orElseGet(() -> Sort.Order.desc("reservationId"));
        final int pageSize = pageable.getPageSize();

        query.where(cursorBooleanExpression(cursor, order))
            .orderBy(orderSpecifiers(order))
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
