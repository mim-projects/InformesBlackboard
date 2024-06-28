package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Courses;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class CoursesRepository {
    @Inject
    @RepositoryClass(Courses.class)
    private Repository<Courses> repository;

    public Courses findByCampusCode(Integer campusCodeId) {
        return repository.findOne("campusCodesId.id", "'" + campusCodeId + "'");
    }

    public List<Courses> findAllDistinct() {
        return repository.findNativeAll("select " +
                "    substring_index(group_concat(courses.id), ',', 1) as id, " +
                "    courses.keyword, " +
                "    trim(courses.name) as name, " +
                "    courses.periods, " +
                "    courses.modality_id, " +
                "    substring_index(group_concat(courses.campus_code_id), ',', 1) as campus_code_id, " +
                "    courses.grades_id, " +
                "    substring_index(group_concat(courses.hash_code), ',', 1) as hash_code " +
                "from courses " +
                "left join campus_codes on campus_codes.id = courses.campus_code_id " +
                "group by courses.keyword, courses.name, courses.periods, courses.modality_id, courses.grades_id, campus_codes.campus_id " +
                "order by trim(courses.name);");
    }

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