package kkomo.member.repository;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import kkomo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(@NotNull String name);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.name = :name")
    int countByName(@Param("name") String name);
}