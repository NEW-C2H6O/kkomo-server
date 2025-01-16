package kkomo.global.support;

import org.springframework.data.domain.Slice;

@FunctionalInterface
public interface CursorProvider<T, C extends Cursor> {

    C provide(Slice<T> slice);
}
