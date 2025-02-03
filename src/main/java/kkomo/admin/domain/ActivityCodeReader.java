package kkomo.admin.domain;

import kkomo.admin.repository.ActivityCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityCodeReader {

    private final ActivityCodeRepository activityCodeRepository;
    private static final String NOT_FOUND_MESSAGE = "생성된 활동 코드가 없습니다.";

    public ActivityCode readLatest() {
        return activityCodeRepository.findTopByOrderByCreatedAtDesc()
            .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
    }
}
