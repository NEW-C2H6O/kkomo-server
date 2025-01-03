package kkomo.admin.repository;

import kkomo.admin.domain.ActivityCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityCodeRepository extends JpaRepository<ActivityCode, Long> {

    boolean existsByValue(String value);

    Optional<ActivityCode> findTopByOrderByCreatedAtDesc();
}
