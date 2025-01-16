package kkomo.ott.domain;

import kkomo.ott.repository.OTTRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OTTReader {

    private static final String OTT_NOT_FOUND = "해당 OTT가 존재하지 않습니다.";
    private final OTTRepository ottRepository;

    public OTT readById(final Long ottId) {
        return ottRepository.findById(ottId)
            .orElseThrow(() -> new IllegalArgumentException(OTT_NOT_FOUND));
    }
}
