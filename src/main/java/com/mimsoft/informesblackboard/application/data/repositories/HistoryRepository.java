package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.History;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class HistoryRepository {
    @Inject
    @RepositoryClass(History.class)
    private Repository<History> repository;

    public History findOrCreate(History history) {
        History item = repository.findOneWhereEqual(
                new String[] {"modalityId.id", "periodsId.id", "gradesId.id", "campusCodesId.id"},
                new Object[]{"'" + history.getModalityId().getId() + "'", "'" + history.getPeriodsId().getId() + "'", "'" + history.getGradesId().getId() + "'", "'" + history.getCampusCodesId().getId() + "'"}
        );
        if (item != null) return item;
        return repository.create(history);
    }
}
