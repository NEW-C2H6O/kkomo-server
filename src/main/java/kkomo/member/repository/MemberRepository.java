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
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(@NotNull String email);

    int countByName(@NotNull String name);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.role = :to where m.role = :from")
    int updateRole(@NotNull MemberRole from, @NotNull MemberRole to);

    int countByRole(@NotNull MemberRole role);
}
