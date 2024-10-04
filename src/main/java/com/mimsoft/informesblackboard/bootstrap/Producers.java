package com.mimsoft.informesblackboard.bootstrap;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
public class Producers {
    @Produces
    @PersistenceContext
    EntityManager entityManager;
}
