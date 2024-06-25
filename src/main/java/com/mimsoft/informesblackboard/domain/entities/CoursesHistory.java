package com.mimsoft.informesblackboard.domain.entities;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import jakarta.persistence.*;

@Entity
@Table(name = "courses_history")
public class CoursesHistory extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "courses_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Courses coursesId;

    @JoinColumn(name = "history_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private History historyId;

    public CoursesHistory() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Courses getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(Courses coursesId) {
        this.coursesId = coursesId;
    }

    public History getHistoryId() {
        return historyId;
    }

    public void setHistoryId(History historyId) {
        this.historyId = historyId;
    }

    @Override
    public String toString() {
        return "CoursesHistory{" +
                "id=" + id +
                ", coursesId=" + coursesId +
                ", historyId=" + historyId +
                '}';
    }
}
