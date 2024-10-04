package com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users;

import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.Modality;
import com.mimsoft.informesblackboard.domain.entities.Roles;
import javax.persistence.*;

@Entity
@SqlResultSetMapping(
        name = "CustomTableCoursesUsers",
        classes = @ConstructorResult(
                targetClass = CustomTableCoursesUsers.class,
                columns = {
                        @ColumnResult(name = "uuid", type = String.class),
                        @ColumnResult(name = "modalityId", type = Modality.class),
                        @ColumnResult(name = "campusId", type = Campus.class),
                        @ColumnResult(name = "rolesId", type = Roles.class),
                        @ColumnResult(name = "value", type = Integer.class)
                }
        )
)
public class CustomTableCoursesUsers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @JoinColumn(name = "modalityId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Modality modalityId;

    @JoinColumn(name = "campusId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Campus campusId;

    @JoinColumn(name = "rolesId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Roles rolesId;

    @Column(name = "value")
    private Integer value;

    public CustomTableCoursesUsers() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
    }

    public Campus getCampusId() {
        return campusId;
    }

    public void setCampusId(Campus campusId) {
        this.campusId = campusId;
    }

    public Roles getRolesId() {
        return rolesId;
    }

    public void setRolesId(Roles rolesId) {
        this.rolesId = rolesId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CustomTableCoursesUsers{" +
                "uuid='" + uuid + '\'' +
                ", modalityId=" + modalityId +
                ", campusId=" + campusId +
                ", rolesId=" + rolesId +
                ", value=" + value +
                '}';
    }
}
