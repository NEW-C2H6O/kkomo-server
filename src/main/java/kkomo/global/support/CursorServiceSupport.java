package kkomo.global.support;

import org.springframework.data.domain.Slice;

public abstract class CursorServiceSupport {

    public <T, C extends Cursor> C provideCursor(
        final Slice<T> slice,
        CursorProvider<T, C> cursorProvider
    ) {
        if (!slice.hasNext()) {
            return null;
        }
        return cursorProvider.provide(slice);
    }
}
