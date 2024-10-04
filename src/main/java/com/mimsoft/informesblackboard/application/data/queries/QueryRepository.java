package com.mimsoft.informesblackboard.application.data.queries;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

public abstract class QueryRepository {
    @Inject
    protected EntityManager entityManager;

    @Resource
    protected UserTransaction userTransaction;
}