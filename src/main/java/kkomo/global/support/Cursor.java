package kkomo.global.support;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Cursor {

    private final Long id;

    @JsonCreator
    public Cursor(@JsonProperty("id") final Long id) {
        this.id = id;
    }

    public static Cursor from(final Long id) {
        if (id == null) {
            return null;
        }
        return new Cursor(id);
    }
}
