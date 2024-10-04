package com.mimsoft.informesblackboard.application.data.queries.custom_periods;

import javax.persistence.*;

@Entity
@SqlResultSetMapping(
        name = "CustomPeriods",
        classes = @ConstructorResult(
                targetClass = CustomPeriods.class,
                columns = {
                        @ColumnResult(name = "uuid", type = String.class),
                        @ColumnResult(name = "name", type = String.class)
                }
        )
)
public class CustomPeriods {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "name")
    private String name;

    public CustomPeriods() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
