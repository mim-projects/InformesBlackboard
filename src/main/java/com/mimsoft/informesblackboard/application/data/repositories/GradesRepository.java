package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class GradesRepository {
    @Inject
    @RepositoryClass(Grades.class)
    private Repository<Grades> repository;

    public List<Grades> findAll() {
        return repository.findAll();
    }
}