package com.mimsoft.boilerplate.bootstrap;

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
