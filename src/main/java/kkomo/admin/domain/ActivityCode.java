package kkomo.admin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kkomo.global.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "activity_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityCode extends BaseEntity {

    @Id
    @Column(name = "activity_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    public static ActivityCode of(final String value) {
        ActivityCode activityCode = new ActivityCode();
        activityCode.value = value;
        return activityCode;
    }
}
