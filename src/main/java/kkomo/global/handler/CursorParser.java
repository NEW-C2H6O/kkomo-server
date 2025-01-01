package kkomo.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import kkomo.global.support.Cursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CursorParser {

    private final ObjectMapper objectMapper;

    public <T extends Cursor> T parse(final Class<T> clazz, final String cursorString) {
        if (cursorString == null) {
            return null;
        }
        try {
            final T result = objectMapper.readValue(Base64.getDecoder().decode(cursorString), clazz);
            if (result == null) {
                return null;
            }
            if (result.getId() == null) {
                throw new IllegalArgumentException("cursor needs default id.");
            }
            return result;
        } catch (final IOException exception) {
            return null;
        }
    }
}
