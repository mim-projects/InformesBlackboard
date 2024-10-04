package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import java.util.List;

@RequestScoped
public class CampusRepository {
    @Inject
    @RepositoryClass(Campus.class)
    private Repository<Campus> repository;

    public List<Campus> findAll() {
        return repository.findAll();
    }

    public void update(Campus item) {
        repository.update(item);
    }

    public Campus findById(Integer id) {
        return repository.findId(id);
    }
}
