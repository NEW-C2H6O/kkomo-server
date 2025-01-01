package kkomo.member.repository;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import kkomo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(@NotNull String name);

    int countByName(@NotNull String nickname);
}
