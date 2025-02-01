package kkomo.ott.domain;

import java.util.Arrays;
import java.util.List;

public record OTTIdAndProfileIds(
    Long ottId,
    List<Long> profileIds
) {

    private static final String SEPARATOR = "_";
    private static final String PROFILE_SEPARATOR = "-";
    private static final String message = """
        은(는) 올바르지 않은 검색 조건 입니다. {OTT_ID}_{PROFILE_ID1}-{PROFILE_ID2}와 같은 형식으로 입력해주세요.
        """;

    public static OTTIdAndProfileIds from(final String param) {
        final String[] params = param.split(SEPARATOR);
        if (params.length >= 3) {
            throw new IllegalArgumentException(param + message);
        }
        final Long ottId = Long.parseLong(params[0]);
        List<Long> profileIds = List.of();
        if (params.length == 2) {
             profileIds = Arrays.stream(params[1].split(PROFILE_SEPARATOR))
                .map(Long::parseLong)
                .toList();
        }
        return new OTTIdAndProfileIds(ottId, profileIds);
    }
}