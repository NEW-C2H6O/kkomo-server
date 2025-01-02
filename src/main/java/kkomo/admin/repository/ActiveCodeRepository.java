package kkomo.admin.repository;

import kkomo.admin.domain.ActiveCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveCodeRepository extends JpaRepository<ActiveCode, Long> {

    boolean existsByValue(String value);
}
