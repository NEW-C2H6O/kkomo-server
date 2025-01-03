package kkomo.admin.service;

import kkomo.admin.controller.dto.response.MemberResponseWithRole;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.SliceResponse;
import kkomo.member.domain.MemberRole;
import kkomo.member.repository.MemberQueryRepository;
import kkomo.member.service.MemberResponseWithRoleCursorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminQueryService {

    private final MemberResponseWithRoleCursorService cursorService;
    private final MemberQueryRepository memberQueryRepository;

    public SliceResponse<MemberResponseWithRole> readBy(
        final CursorPageable<? extends Cursor> pageable
    ) {
        final Slice<MemberResponseWithRole> response = memberQueryRepository.findByRole(
            MemberRole.ROLE_ADMIN, pageable
        );
        final String cursor = cursorService.serializeCursor(response);
        return SliceResponse.of(response, cursor);
    }
}
