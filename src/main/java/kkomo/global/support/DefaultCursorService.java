package kkomo.global.support;

import org.springframework.data.domain.Slice;

public abstract class DefaultCursorService {

    public <T> Cursor extractCursor(final Slice<T> slice, LastIdExtractor<T> lastIdExtractor) {
        if (!slice.hasNext()) {
            return null;
        }
        final Long id = lastIdExtractor.extract(slice);
        return Cursor.from(id);
    }
}
