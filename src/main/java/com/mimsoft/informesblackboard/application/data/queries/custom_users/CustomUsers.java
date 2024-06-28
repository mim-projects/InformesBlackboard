package com.mimsoft.informesblackboard.application.data.queries.custom_users;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import jakarta.persistence.*;

@Entity
@SqlResultSetMapping(
        name = "CustomWorkers",
        classes = @ConstructorResult(
                targetClass = CustomUsers.class,
                columns = {
                        @ColumnResult(name = "uuid", type = String.class),
                        @ColumnResult(name = "id", type = String.class),
                }
        )
)
public class CustomUsers extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    public Integer getId() {
        return 0;
    }

    public void setId(Integer id) {

    }
}
