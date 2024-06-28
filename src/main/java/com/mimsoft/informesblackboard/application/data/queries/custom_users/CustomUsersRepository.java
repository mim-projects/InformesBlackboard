package com.mimsoft.informesblackboard.application.data.queries.custom_users;

import com.mimsoft.informesblackboard.application.data.queries.QueryRepository;
import jakarta.enterprise.context.RequestScoped;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class CustomUsersRepository extends QueryRepository {
    public List<CustomUsers> findAll() {
        String query = "select " +
                "    uuid() as uuid, " +
                "    substring_index(group_concat(users.id), ',', 1) as id, " +
                "    users.keyword as userKeyword, " +
                "    users.periods as period, " +
                "    roles.name as role, " +
                "    modality.description as modality, " +
                "    campus.name as campus, " +
                "    grades.name as grade, " +
                "    group_concat(users.keyword_course) as coursesKeyword " +
                "from users " +
                "    left join roles on roles.id = users.roles_id " +
                "    left join modality on modality.id = users.modality_id " +
                "    left join campus_codes on campus_codes.id = users.campus_code_id " +
                "    left join campus on campus.id = campus_codes.campus_id " +
                "    left join grades on grades.id = users.grades_id " +
                "group by users.keyword, users.periods, roles.id, modality.id, campus.id, grades.id " +
                "order by users.keyword, users.periods, roles.id, campus.id, modality.id;";
        try { return entityManager.createNativeQuery(query, CustomUsers.class).getResultList(); }
        catch (Exception ignore) { return new ArrayList<>(); }
    }
}
