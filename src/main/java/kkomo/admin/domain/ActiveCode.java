package kkomo.admin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kkomo.global.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "active_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActiveCode extends BaseEntity {

    @Id
    @Column(name = "active_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    public static ActiveCode of(final String value) {
        ActiveCode activeCode = new ActiveCode();
        activeCode.value = value;
        return activeCode;
    }
}
