package kkomo.admin.domain;

import kkomo.admin.repository.ActivityCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ActivityCodeGenerator {

    private final ActivityCodeRepository activityCodeRepository;
    private static final SecureRandom RANDOM = new SecureRandom();

    public ActivityCode generate() {
        return ActivityCode.from(generateValue());
    }

    private String generateValue() {
        String value = generateRandomValue();
        while (activityCodeRepository.existsByValue(value)) {
            value = generateRandomValue();
        }
        return value;
    }

    private String generateRandomValue() {
        return String.format("%04d", 1000 + RANDOM.nextInt(9000));
    }
}
