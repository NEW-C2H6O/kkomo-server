package kkomo.global.support;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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
}
