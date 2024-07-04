package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.application.data.queries.custom_keyword_value.CustomKeywordValueRepository;
import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import com.mimsoft.informesblackboard.domain.entities.Roles;
import com.mimsoft.informesblackboard.domain.entities.Users;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class UsersRepository {
    @Inject
    @RepositoryClass(Users.class)
    private Repository<Users> repository;
    @Inject
    private CustomKeywordValueRepository customKeywordValueRepository;

    public Users findById(String id) {
        return repository.findOne("id", "'" + id + "'");
    }

    public List<Users> findAll() {
        return repository.findAll();
    }

    public void createIgnore(Users users) {
        try {
            String hashCode = users.getKeyword() + users.getPeriods() + users.getKeywordCourse() + + users.getRolesId().getId() + users.getModalityId().getId() + users.getCampusCodesId().getId() + users.getGradesId().getId();
            hashCode = hashCode.trim().replaceAll("_", "").replaceAll(" ", "");
            String query = "insert ignore into users (" +
                    "keyword, periods, keyword_course, roles_id, modality_id, campus_code_id, grades_id, hash_code" +
                    ") values (" +
                    "'" + users.getKeyword() + "'," +
                    "'" + users.getPeriods() + "'," +
                    "'" + users.getKeywordCourse() + "'," +
                    "'" + users.getRolesId().getId() + "'," +
                    "'" + users.getModalityId().getId() + "'," +
                    "'" + users.getCampusCodesId().getId() + "'," +
                    "'" + users.getGradesId().getId() + "'," +
                    "'" + hashCode + "'" +
                    ");";
            repository.executeNativeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Users findByCampusCode(Integer campusCodeId) {
        return repository.findOne("campusCodesId.id", "'" + campusCodeId + "'");
    }

    public int findTotalByRolesCampusPeriodGrade(Roles roles, Campus campus, String period, Grades grades) {
        String query = " " +
                "select " +
                "    uuid() as id, " +
                "    '' as keyword, " +
                "    count(t.keyword) as value " +
                "from ( " +
                "    select " +
                "        users.keyword " +
                "    from users " +
                "        left join campus_codes on campus_codes.id = users.campus_code_id " +
                "    where " +
                "        campus_codes.campus_id = '" + campus.getId() + "' and " +
                "        periods = '" + period + "' and " +
                "        grades_id = '" + grades.getId() + "' and " +
                "        roles_id = '" + roles.getId() + "' " +
                "    group by users.keyword " +
                ") as t;";
        return (int) customKeywordValueRepository.getValueSingleQuery(0, query);
    }

    public int findTotalByCampusPeriodGrade(Campus campus, String period, Grades grades) {
        String query = " " +
                "select " +
                "    uuid() as id, " +
                "    '' as keyword, " +
                "    count(t.keyword) as value " +
                "from ( " +
                "    select " +
                "        users.keyword " +
                "    from users " +
                "        left join campus_codes on campus_codes.id = users.campus_code_id " +
                "    where " +
                "        campus_codes.campus_id = '" + campus.getId() + "' and " +
                "        periods = '" + period + "' and " +
                "        grades_id = '" + grades.getId() + "' " +
                "    group by users.keyword " +
                ") as t;";
        return (int) customKeywordValueRepository.getValueSingleQuery(0, query);
    }
}
