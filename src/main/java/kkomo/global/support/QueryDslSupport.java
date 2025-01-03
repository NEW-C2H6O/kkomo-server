package kkomo.global.support;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public abstract class QueryDslSupport {

    private EntityManager entityManager;
    protected JPAQueryFactory queryFactory;

    @Autowired
    public void setEntityManager(final EntityManager entityManager) {
        Assert.notNull(entityManager, "EntityManager must not be null.");
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
    }

    @PostConstruct
    public void validate() {
        Assert.notNull(entityManager, "EntityManager must not be null.");
        Assert.notNull(queryFactory, "QueryFactory must not be null.");
    }

    private boolean removeIfContentHasNext(final List<?> content, final int size) {
        if (content.size() > size) {
            content.remove(size);
            return true;
        }
        return false;
    }

    private boolean isFirst(final CursorPageable<? extends Cursor> pageable) {
        return pageable.getCursor() == null;
    }

    protected <T> Slice<T> paginate(
        final List<T> content,
        final CursorPageable<? extends Cursor> pageable
    ) {
        final List<T> mutable = new ArrayList<>(content);
        final int size = pageable.getPageSize();
        final boolean hasNext = removeIfContentHasNext(mutable, size);
        final PageRequest request = PageRequest.of(
            isFirst(pageable) ? 0 : 1,
            size
        );
        return new SliceImpl<>(mutable, request, hasNext);
    }
}
