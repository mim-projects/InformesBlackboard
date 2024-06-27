package com.mimsoft.informesblackboard.domain.entities;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Courses extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "name")
    private String name;

    @Column(name = "periods")
    private int periods;

    @JoinColumn(name = "modality_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Modality modalityId;

    @JoinColumn(name = "campus_code_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CampusCodes campusCodesId;

    @JoinColumn(name = "grades_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grades gradesId;

    @Column(name = "hash_code")
    private String hashCode;

    public Courses() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
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

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", name='" + name + '\'' +
                ", periods=" + periods +
                ", modalityId=" + modalityId +
                ", campusCodesId=" + campusCodesId +
                ", gradesId=" + gradesId +
                ", hashCode='" + hashCode + '\'' +
                '}';
    }
}
