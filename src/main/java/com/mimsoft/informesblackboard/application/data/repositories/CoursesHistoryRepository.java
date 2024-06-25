package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.CoursesHistory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CoursesHistoryRepository {
    public static int counter = 0;

    @Inject
    @RepositoryClass(CoursesHistory.class)
    private Repository<CoursesHistory> repository;

    public CoursesHistory findOrCreate(CoursesHistory coursesHistory) {
        CoursesHistory item = repository.findOneWhereEqual(
                new String[]{"coursesId.id", "historyId.id"},
                new Object[]{"'" + coursesHistory.getCoursesId().getId() + "'", "'" + coursesHistory.getHistoryId().getId() + "'"}
        );
        if (item != null) return item;
        return repository.create(coursesHistory);
    }
}
