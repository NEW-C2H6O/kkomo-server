package kkomo.member.repository;

import kkomo.admin.controller.dto.response.MemberResponse;
import kkomo.global.support.Base64Encoder;
import kkomo.global.support.Cursor;
import kkomo.global.support.DefaultCursorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberResponseCursorService extends DefaultCursorService {

    private final Base64Encoder base64Encoder;

    public String encodeCursor(final Slice<MemberResponse> slice) {
        final Cursor cursor = extractCursor(
            slice,
            s -> s.getContent()
                .stream()
                .reduce((first, second) -> second)
                .map(MemberResponse::memberId)
                .orElse(null)
        );
        return base64Encoder.encode(cursor);
    }
}
