package kkomo.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import kkomo.admin.controller.dto.response.MemberResponse;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.QueryDslSupport;
import kkomo.member.domain.MemberRole;
import kkomo.member.domain.QMember;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class MemberQueryDslRepository extends QueryDslSupport implements MemberQueryRepository {

    private static final QMember member = QMember.member;

    private BooleanExpression cursorBooleanExpression(final CursorPageable<? extends Cursor> pageable) {
        if (!pageable.hasCursor()) {
            return null;
        }
        final Long id = pageable.getCursor().getId();
        return member.id.lt(id);
    }

    private BooleanExpression memberRoleEq(final MemberRole role) {
        if (role == null) {
            return null;
        }
        return member.role.eq(role);
    }

    private List<MemberResponse> fetchContent(
        final MemberRole role,
        final CursorPageable<? extends Cursor> pageable
    ) {
        return queryFactory.selectFrom(member)
            .where(memberRoleEq(role), cursorBooleanExpression(pageable))
            .orderBy(member.id.desc())
            .limit(pageable.getPageSize() + 1)
            .fetch()
            .stream()
            .map(MemberResponse::from)
            .toList();
    }

    @Override
    public Slice<MemberResponse> findByRole(
        final MemberRole role,
        final CursorPageable<? extends Cursor> pageable
    ) {
        final List<MemberResponse> content = fetchContent(role, pageable);
        return paginate(content, pageable);
    }
}
