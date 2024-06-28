package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Roles;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class RolesRepository {
    @Inject
    @RepositoryClass(Roles.class)
    private Repository<Roles> repository;

    public List<Roles> findAll() {
        return repository.findAll();
    }
}