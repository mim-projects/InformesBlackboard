package com.mimsoft.informesblackboard.bootstrap;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Dependent
public class Producers {
    @Produces
    @PersistenceContext
    EntityManager entityManager;
}
