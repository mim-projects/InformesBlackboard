package com.mimsoft.informesblackboard.domain.entities;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import jakarta.persistence.*;

@Entity
@Table(name = "history")
public class History extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "modality_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Modality modalityId;

    @JoinColumn(name = "periods_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Periods periodsId;

    @JoinColumn(name = "grades_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grades gradesId;

    @JoinColumn(name = "campus_codes_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CampusCodes campusCodesId;

    public History() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
    }

    public Periods getPeriodsId() {
        return periodsId;
    }

    public void setPeriodsId(Periods periodsId) {
        this.periodsId = periodsId;
    }

    public Grades getGradesId() {
        return gradesId;
    }

    public void setGradesId(Grades gradesId) {
        this.gradesId = gradesId;
    }

    public CampusCodes getCampusCodesId() {
        return campusCodesId;
    }

    public void setCampusCodesId(CampusCodes campusCodesId) {
        this.campusCodesId = campusCodesId;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", modalityId=" + modalityId +
                ", periodsId=" + periodsId +
                ", gradesId=" + gradesId +
                ", campusCodesId=" + campusCodesId +
                '}';
    }
}
