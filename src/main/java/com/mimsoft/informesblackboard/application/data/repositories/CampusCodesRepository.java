package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.CampusCodes;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class CampusCodesRepository {
    @Inject
    @RepositoryClass(CampusCodes.class)
    private Repository<CampusCodes> repository;

    public List<CampusCodes> findAll() {
        return repository.findAll();
    }
}
