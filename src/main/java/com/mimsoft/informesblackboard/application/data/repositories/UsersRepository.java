package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Users;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class UsersRepository {
    @Inject
    @RepositoryClass(Users.class)
    private Repository<Users> repository;

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
}
