package com.mimsoft.informesblackboard.domain.entities;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import jakarta.persistence.*;

@Entity
@Table(name = "campus_codes")
public class CampusCodes extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @JoinColumn(name = "campus_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Campus campusId;

    public CampusCodes() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Campus getCampusId() {
        return campusId;
    }

    public void setCampusId(Campus campusId) {
        this.campusId = campusId;
    }

    @Override
    public String toString() {
        return "CampusCodes{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", campusId=" + campusId +
                '}';
    }
}
