package com.mimsoft.informesblackboard.application.data.queries.custom_users;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@SqlResultSetMapping(
        name = "CustomUsers",
        classes = @ConstructorResult(
                targetClass = CustomUsers.class,
                columns = {
                        @ColumnResult(name = "uuid", type = String.class),
                        @ColumnResult(name = "id", type = String.class),
                        @ColumnResult(name = "userKeyword", type = String.class),
                        @ColumnResult(name = "period", type = String.class),
                        @ColumnResult(name = "role", type = String.class),
                        @ColumnResult(name = "modality", type = String.class),
                        @ColumnResult(name = "campus", type = String.class),
                        @ColumnResult(name = "grade", type = String.class),
                        @ColumnResult(name = "coursesKeyword", type = String.class)
                }
        )
)
public class CustomUsers extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "id")
    private Integer id;

    @Column(name = "userKeyword")
    private String userKeyword;

    @Column(name = "period")
    private String period;

    @Column(name = "role")
    private String role;

    @Column(name = "modality")
    private String modality;

    @Column(name = "campus")
    private String campus;

    @Column(name = "grade")
    private String grade;

    @Column(name = "coursesKeyword")
    private String coursesKeyword;

    public CustomUsers() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserKeyword() {
        return userKeyword;
    }

    public void setUserKeyword(String userKeyword) {
        this.userKeyword = userKeyword;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCoursesKeyword() {
        return coursesKeyword;
    }

    public void setCoursesKeyword(String coursesKeyword) {
        this.coursesKeyword = coursesKeyword;
    }

    public List<String> getListCoursesKeyword() {
        return new ArrayList<>(Arrays.asList(getCoursesKeyword().split(",")));
    }
}
