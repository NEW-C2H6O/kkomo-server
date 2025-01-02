package kkomo.member.repository;

import jakarta.validation.constraints.NotNull;
import kkomo.member.domain.Member;
import kkomo.member.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(@NotNull String name);

    int countByName(@NotNull String nickname);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.role = 'ROLE_DEACTIVATED' where m.role = :role")
    int deactivateAllOf(@NotNull MemberRole role);
}
