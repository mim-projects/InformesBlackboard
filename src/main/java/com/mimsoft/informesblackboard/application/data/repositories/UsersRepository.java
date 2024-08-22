package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import com.mimsoft.informesblackboard.domain.entities.Users;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestScoped
public class UsersRepository {
    @Inject
    @RepositoryClass(Users.class)
    private Repository<Users> repository;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Users findById(String id) {
        return repository.findOne("id", "'" + id + "'");
    }

    public List<Users> findAll() {
        return repository.findAll();
    }

    public String createIgnoreQuery(Users users) {
        String hashCode = users.getKeyword() + users.getPeriods() + users.getKeywordCourse() + + users.getRolesId().getId() + users.getModalityId().getId() + users.getCampusCodesId().getId() + users.getGradesId().getId();
        hashCode = hashCode.trim().replaceAll("_", "").replaceAll(" ", "");
        return  "insert ignore into users (" +
                "keyword, periods, keyword_course, roles_id, modality_id, campus_code_id, grades_id, hash_code, dated_at " +
                ") values (" +
                "'" + users.getKeyword() + "'," +
                "'" + users.getPeriods() + "'," +
                "'" + users.getKeywordCourse() + "'," +
                "'" + users.getRolesId().getId() + "'," +
                "'" + users.getModalityId().getId() + "'," +
                "'" + users.getCampusCodesId().getId() + "'," +
                "'" + users.getGradesId().getId() + "'," +
                "'" + hashCode + "'," +
                "'" + sdf.format(users.getDatedAt()) + "' " +
                ");";
    }

    public Users findByCampusCode(Integer campusCodeId) {
        return repository.findOne("campusCodesId.id", "'" + campusCodeId + "'");
    }

    public void removeAllByPeriodCampusGrades(String periods, Date datedAt, Campus campusId, Grades gradesId) {
        String query = "delete users from users left join campus_codes on campus_codes.id = users.campus_code_id where " +
                "periods = '" + periods + "' and " +
                "grades_id = '" + gradesId.getId() + "' and " +
                "campus_id = '" + campusId.getId() + "' and " +
                "dated_at like '" +  new SimpleDateFormat("yyyy-MM").format(datedAt) + "%' ";
        repository.executeNativeQuery(query);
    }
}
