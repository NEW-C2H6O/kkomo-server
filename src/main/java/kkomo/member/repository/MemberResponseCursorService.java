package kkomo.member.repository;

import kkomo.admin.controller.dto.response.MemberResponse;
import kkomo.global.support.Base64Encoder;
import kkomo.global.support.Cursor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberResponseCursorService {

    private final Base64Encoder base64Encoder;

    public String encodeCursor(final Slice<MemberResponse> slice) {
        final List<MemberResponse> content = slice.getContent();
        if (content.isEmpty()) {
            return null;
        }
        final Long id = content.getLast().memberId();
        final Cursor cursor = Cursor.from(id);
        return base64Encoder.encode(cursor);
    }
}
