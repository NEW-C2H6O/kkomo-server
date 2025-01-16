package kkomo.admin.service;

import kkomo.admin.controller.dto.response.MemberResponse;
import kkomo.global.support.Cursor;
import kkomo.global.support.CursorPageable;
import kkomo.global.support.SliceResponse;
import kkomo.member.domain.MemberRole;
import kkomo.member.repository.MemberQueryRepository;
import kkomo.member.repository.MemberResponseCursorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminQueryService {

    private final MemberResponseCursorService cursorService;
    private final MemberQueryRepository memberQueryRepository;

    public SliceResponse<MemberResponse> readAdminsBy(
        final CursorPageable<? extends Cursor> pageable
    ) {
        final Slice<MemberResponse> response = memberQueryRepository.findByRole(
            MemberRole.ROLE_ADMIN, pageable
        );
        final String cursor = cursorService.encodeCursor(response);
        return SliceResponse.of(response, cursor);
    }

    public SliceResponse<MemberResponse> readMembersBy(
        final CursorPageable<? extends Cursor> pageable
    ) {
        final Slice<MemberResponse> response = memberQueryRepository.findByRole(null, pageable);
        final String cursor = cursorService.encodeCursor(response);
        return SliceResponse.of(response, cursor);
    }
}
