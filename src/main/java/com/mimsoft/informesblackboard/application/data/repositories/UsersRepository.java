package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.application.data.queries.custom_keyword_value.CustomKeywordValueRepository;
import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.Grades;
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

    public void removeAllByPeriodCampusGrades(String periods, Campus campusId, Grades gradesId) {
        String query = "delete users from users left join campus_codes on campus_codes.id = users.campus_code_id where " +
                "periods = '" + periods + "' and " +
                "grades_id = '" + gradesId.getId() + "' and " +
                "campus_id = '" + campusId.getId() + "'";
        repository.executeNativeQuery(query);
    }
}
