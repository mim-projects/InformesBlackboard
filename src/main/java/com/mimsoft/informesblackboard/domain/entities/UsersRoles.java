package com.mimsoft.informesblackboard.domain.entities;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import jakarta.persistence.*;

@Entity
@Table(name = "users_roles")
public class UsersRoles extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users usersId;

    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Roles rolesId;

    public UsersRoles() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUsersId() {
        return usersId;
    }

    public void setUsersId(Users usersId) {
        this.usersId = usersId;
    }

    public Roles getRolesId() {
        return rolesId;
    }

    public void setRolesId(Roles rolesId) {
        this.rolesId = rolesId;
    }

    @Override
    public String toString() {
        return "UsersRoles{" +
                "id=" + id +
                ", usersId=" + usersId +
                ", rolesId=" + rolesId +
                '}';
    }
}
