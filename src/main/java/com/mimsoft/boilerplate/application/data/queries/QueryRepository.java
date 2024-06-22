package com.mimsoft.boilerplate.application.data.queries;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.UserTransaction;

public abstract class QueryRepository {
    @Inject
    protected EntityManager entityManager;

    @Resource
    protected UserTransaction userTransaction;
}