package com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mimsoft.informesblackboard.application.data.queries.QueryRepository;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheCallback;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheServices;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheStaticData;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class CustomTableCoursesUsersRepository extends QueryRepository {
    @Inject
    private SimulateCacheServices simulateCacheServices;

    private List<CustomTableCoursesUsers> getCacheList(String keyword, SimulateCacheCallback<CustomTableCoursesUsers> simulateCacheCallback) {
        List<CustomTableCoursesUsers> cache = new Gson().fromJson(simulateCacheServices.get(keyword), new TypeToken<>() {});
        if (cache == null || cache.isEmpty()) {
            cache = simulateCacheCallback.execute();
            simulateCacheServices.put(keyword, cache);
        }
        return cache;
    }

    public List<CustomTableCoursesUsers> findCourses(String period, Integer month, int[] modality, Integer gradesId) {
        String keyword = SimulateCacheStaticData.CreateKeywordCoursesFilters(period, month, modality, gradesId);
        return getCacheList(keyword, () -> {
            String helper = "";
            for (int current: modality) helper += "modality_id = '" + current + "' or ";
            if (!helper.isEmpty()) helper = " and (" + helper.substring(0, helper.length() - 3) + ") ";

            String query = "select " +
                    "    uuid() as uuid, " +
                    "    modality_id as modalityId, " +
                    "    campus_id as campusId, " +
                    "    null as rolesId, " +
                    "    count(distinct hash_code) as value " +
                    "from courses " +
                    "left join campus_codes on campus_codes.id = courses.campus_code_id " +
                    "where " +
                    "    courses.periods = '" + period + "' and " +
                    "    courses.grades_id = '" + gradesId + "' and " +
                    "    courses.periods like concat(date_format(courses.dated_at, '%Y'), '%') and " +
                    "    month(courses.dated_at) = '" + month + "' " + helper +
                    "group by campus_id, grades_id, modality_id " +
                    "order by value desc;";
            try {
                return entityManager.createNativeQuery(query, CustomTableCoursesUsers.class).getResultList();
            } catch (Exception ignore) {
                return new ArrayList<>();
            }
        });
    }

    public List<CustomTableCoursesUsers> findUsers(String period, Integer month, int[] roles, Integer gradesId) {
        String keyword = SimulateCacheStaticData.CreateKeywordUsersFilters(period, month, roles, gradesId);
        return getCacheList(keyword, () -> {
            String helper = "";
            for (int current: roles) helper += "roles_id = '" + current + "' or ";
            if (!helper.isEmpty()) helper = " and (" + helper.substring(0, helper.length() - 3) + ") ";

            String query = "select " +
                    "    uuid() as uuid, " +
                    "    null as modalityId, " +
                    "    campus_id as campusId, " +
                    "    roles_id as rolesId, " +
                    "    count(distinct keyword) as value " +
                    "from users " +
                    "left join campus_codes on campus_codes.id = users.campus_code_id " +
                    "where " +
                    "    users.periods = '" + period + "' and " +
                    "    users.grades_id = '" + gradesId + "' and " +
                    "    users.periods like concat(date_format(users.dated_at, '%Y'), '%') and " +
                    "    month(users.dated_at) = '" + month + "' " + helper +
                    "group by campus_id, grades_id, roles_id " +
                    "order by value desc;";
            try {
                return entityManager.createNativeQuery(query, CustomTableCoursesUsers.class).getResultList();
            } catch (Exception ignore) {
                return new ArrayList<>();
            }
        });
    }
}