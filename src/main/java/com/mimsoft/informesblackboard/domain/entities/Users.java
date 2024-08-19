package com.mimsoft.informesblackboard.domain.entities;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class Users extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "periods")
    private int periods;

    @Column(name = "keyword_course")
    private String keywordCourse;

    @JoinColumn(name = "modality_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Modality modalityId;

    @JoinColumn(name = "campus_code_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CampusCodes campusCodesId;

    @JoinColumn(name = "grades_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grades gradesId;

    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Roles rolesId;

    @Column(name = "hash_code", unique = true)
    private String hashCode;

    @Column(name = "dated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datedAt;

    public Users() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public String getKeywordCourse() {
        return keywordCourse;
    }

    public void setKeywordCourse(String keywordCourse) {
        this.keywordCourse = keywordCourse;
    }

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
    }

    public CampusCodes getCampusCodesId() {
        return campusCodesId;
    }

    public void setCampusCodesId(CampusCodes campusCodesId) {
        this.campusCodesId = campusCodesId;
    }

    public Grades getGradesId() {
        return gradesId;
    }

    public void setGradesId(Grades gradesId) {
        this.gradesId = gradesId;
    }

    public Roles getRolesId() {
        return rolesId;
    }

    public void setRolesId(Roles rolesId) {
        this.rolesId = rolesId;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Date getDatedAt() {
        return datedAt;
    }

    public void setDatedAt(Date datedAt) {
        this.datedAt = datedAt;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", periods=" + periods +
                ", keywordCourse='" + keywordCourse + '\'' +
                ", modalityId=" + modalityId +
                ", campusCodesId=" + campusCodesId +
                ", gradesId=" + gradesId +
                ", rolesId=" + rolesId +
                ", hashCode='" + hashCode + '\'' +
                ", datedAt=" + datedAt +
                '}';
    }
}
