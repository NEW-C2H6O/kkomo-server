package kkomo.global.support;

import org.springframework.data.domain.Sort;

public interface CursorPageable<T extends Cursor> {

    int getPageSize();
    boolean hasCursor();
    T getCursor();
    Sort getSort();
}