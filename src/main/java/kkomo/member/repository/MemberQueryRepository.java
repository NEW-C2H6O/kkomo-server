package kkomo.member.repository;

import kkomo.admin.controller.dto.response.MemberResponseWithRole;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorPageable;
import kkomo.member.domain.MemberRole;
import org.springframework.data.domain.Slice;

public interface MemberQueryRepository  {

    Slice<MemberResponseWithRole> findByRole(MemberRole role, CursorPageable<? extends Cursor> pageable);
}
