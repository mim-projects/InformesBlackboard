package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Courses;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CoursesRepository {
    @Inject
    @RepositoryClass(Courses.class)
    private Repository<Courses> repository;

    public void createIgnore(Courses courses) {
        try {
            String query = "insert ignore into courses (" +
                    "keyword, periods, name, modality_id, campus_code_id, grades_id, hash_code" +
                    ") values (" +
                    "'" + courses.getKeyword() + "'," +
                    "'" + courses.getPeriods() + "'," +
                    "'" + courses.getName() + "'," +
                    "'" + courses.getModalityId().getId() + "'," +
                    "'" + courses.getCampusCodesId().getId() + "'," +
                    "'" + courses.getGradesId().getId() + "'," +
                    "'" + courses.getHashCode() + "'" +
                    ");";
            repository.executeNativeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
