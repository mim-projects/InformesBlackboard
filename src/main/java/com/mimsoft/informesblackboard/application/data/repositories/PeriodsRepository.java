package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Periods;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class PeriodsRepository {
    @Inject
    @RepositoryClass(Periods.class)
    private Repository<Periods> repository;

    public Periods findOrCreate(String name) {
        Periods item = repository.findOne("name", "'" + name + "'");
        if (item != null) return item;
        item = new Periods();
        item.setName(name);
        return repository.create(item);
    }
}
