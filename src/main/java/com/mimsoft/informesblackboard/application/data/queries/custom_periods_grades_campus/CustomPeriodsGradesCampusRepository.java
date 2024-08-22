package com.mimsoft.informesblackboard.application.data.queries.custom_periods_grades_campus;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheStaticData.CreateKeywordUsersCampusTable;

@RequestScoped
public class CustomPeriodsGradesCampusRepository {
    @Inject
    private EntityManager entityManager;

    public List<CustomPeriodsGradesCampus> getAll() {
        HashMap<String, CustomPeriodsGradesCampus> list = new HashMap<>();
        for (CustomPeriodsGradesCampus item: getAllFromCourses()) {
            item.setKeywordCache(CreateKeywordUsersCampusTable(item.getPeriods(), item.getDatedAt(), item.getGradesId().getId(), item.getCampusId().getId(), item.getTable()));
            list.put(item.getKeywordCache(), item);
        }
        for (CustomPeriodsGradesCampus item: getAllFromUsers()) {
            item.setKeywordCache(CreateKeywordUsersCampusTable(item.getPeriods(), item.getDatedAt(), item.getGradesId().getId(), item.getCampusId().getId(), item.getTable()));
            list.put(item.getKeywordCache(), item);
        }
        return new ArrayList<>(list.values());
    }

    private List<CustomPeriodsGradesCampus> getAllFromUsers() {
        String query = "select uuid() as uuid, periods, concat(date_format(dated_at, '%Y-%m'), '-01') as dated_at, grades_id as gradesId, campus_id as campusId, 'users' as `table`, null as keyword_cache " +
                "from users " +
                "left join campus_codes on campus_codes.id = campus_code_id " +
                "group by periods, concat(date_format(dated_at, '%Y-%m'), '-01'), grades_id, campus_id;";
        try { return entityManager.createNativeQuery(query, CustomPeriodsGradesCampus.class).getResultList(); }
        catch (Exception e) { return new ArrayList<>(); }
    }

    private List<CustomPeriodsGradesCampus> getAllFromCourses() {
        String query = "select uuid() as uuid, periods, concat(date_format(dated_at, '%Y-%m'), '-01') as dated_at, grades_id as gradesId, campus_id as campusId, 'courses' as `table`, null as keyword_cache " +
                "from courses " +
                "left join campus_codes on campus_codes.id = campus_code_id " +
                "group by periods, concat(date_format(dated_at, '%Y-%m'), '-01'), grades_id, campus_id;";
        try { return entityManager.createNativeQuery(query, CustomPeriodsGradesCampus.class).getResultList(); }
        catch (Exception e) { return new ArrayList<>(); }
    }
}
