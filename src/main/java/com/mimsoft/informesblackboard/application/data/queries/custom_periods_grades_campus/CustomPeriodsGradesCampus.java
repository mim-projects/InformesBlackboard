package com.mimsoft.informesblackboard.application.data.queries.custom_periods_grades_campus;

import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import jakarta.persistence.*;

@Entity
@SqlResultSetMapping(
        name = "CustomPeriodsGradesCampus",
        classes = @ConstructorResult(
                targetClass = CustomPeriodsGradesCampus.class,
                columns = {
                        @ColumnResult(name = "uuid", type = String.class),
                        @ColumnResult(name = "periods", type = String.class),
                        @ColumnResult(name = "gradesId", type = Grades.class),
                        @ColumnResult(name = "campusId", type = Campus.class),
                        @ColumnResult(name = "table", type = String.class)
                }
        )
)
public class CustomPeriodsGradesCampus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "periods")
    private String periods;

    @JoinColumn(name = "gradesId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grades gradesId;

    @JoinColumn(name = "campusId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Campus campusId;

    @Column(name = "table")
    private String table;

    public CustomPeriodsGradesCampus() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public Grades getGradesId() {
        return gradesId;
    }

    public void setGradesId(Grades gradesId) {
        this.gradesId = gradesId;
    }

    public Campus getCampusId() {
        return campusId;
    }

    public void setCampusId(Campus campusId) {
        this.campusId = campusId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
