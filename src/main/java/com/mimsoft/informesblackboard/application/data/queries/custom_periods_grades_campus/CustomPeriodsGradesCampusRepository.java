package com.mimsoft.informesblackboard.application.data.queries.custom_periods_grades_campus;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestScoped
public class CustomPeriodsGradesCampusRepository {
    @Inject
    private EntityManager entityManager;

    public List<CustomPeriodsGradesCampus> getAll() {
        HashMap<String, CustomPeriodsGradesCampus> list = new HashMap<>();
        for (CustomPeriodsGradesCampus item: getAllFromCourses()) {
            list.put(item.getPeriods() + "_" + item.getGradesId().getId() + "_" + item.getCampusId().getId() + "_" + item.getTable(), item);
        }
        for (CustomPeriodsGradesCampus item: getAllFromUsers()) {
            list.put(item.getPeriods() + "_" + item.getGradesId().getId() + "_" + item.getCampusId().getId() + "_" + item.getTable(), item);
        }
        return new ArrayList<>(list.values());
    }

    public List<CustomPeriodsGradesCampus> getAllFromUsers() {
        String query = "select uuid() as uuid, periods, grades_id as gradesId, campus_id as campusId, 'users' as `table` " +
                "from users " +
                "left join campus_codes on campus_codes.id = campus_code_id " +
                "group by periods, grades_id, campus_id;";
        try { return entityManager.createNativeQuery(query, CustomPeriodsGradesCampus.class).getResultList(); }
        catch (Exception e) { return new ArrayList<>(); }
    }

    public List<CustomPeriodsGradesCampus> getAllFromCourses() {
        String query = "select uuid() as uuid, periods, grades_id as gradesId, campus_id as campusId, 'courses' as `table` " +
                "from courses " +
                "left join campus_codes on campus_codes.id = campus_code_id " +
                "group by periods, grades_id, campus_id;";
        try { return entityManager.createNativeQuery(query, CustomPeriodsGradesCampus.class).getResultList(); }
        catch (Exception e) { return new ArrayList<>(); }
    }
}
