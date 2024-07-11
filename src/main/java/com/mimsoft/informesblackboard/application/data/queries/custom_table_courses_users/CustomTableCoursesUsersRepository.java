package com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users;

import com.mimsoft.informesblackboard.application.data.queries.QueryRepository;
import jakarta.enterprise.context.RequestScoped;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class CustomTableCoursesUsersRepository extends QueryRepository {
    public List<CustomTableCoursesUsers> findCourses(String period, Integer gradesId) {
        String query = "select " +
                "    uuid() as uuid, " +
                "    modality_id as modalityId, " +
                "    campus_id as campusId, " +
                "    null as rolesId, " +
                "    count(distinct hash_code) as value " +
                "from courses " +
                "left join campus_codes on campus_codes.id = courses.campus_code_id " +
                "where courses.periods = '" + period + "' and courses.grades_id = '" + gradesId + "' " +
                "group by campus_id, modality_id " +
                "order by value desc;";
        try {
            return entityManager.createNativeQuery(query, CustomTableCoursesUsers.class).getResultList();
        } catch (Exception ignore) {
            return new ArrayList<>();
        }
    }

    public List<CustomTableCoursesUsers> findUsers(String period, Integer gradesId) {
        String query = "select " +
                "    uuid() as uuid, " +
                "    null as modalityId, " +
                "    campus_id as campusId, " +
                "    roles_id as rolesId, " +
                "    count(distinct keyword) as value " +
                "from users " +
                "left join campus_codes on campus_codes.id = users.campus_code_id " +
                "where users.periods = '" + period + "' and users.grades_id = '" + gradesId + "' " +
                "group by campus_id, grades_id, roles_id " +
                "order by value desc;";
        try {
            return entityManager.createNativeQuery(query, CustomTableCoursesUsers.class).getResultList();
        } catch (Exception ignore) {
            return new ArrayList<>();
        }
    }
}
