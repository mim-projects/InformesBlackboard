package com.mimsoft.informesblackboard.domain.entities;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import jakarta.persistence.*;

@Entity
@Table(name = "users_history")
public class UsersHistory extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "users_roles_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UsersRoles usersRolesId;

    @JoinColumn(name = "history_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private History historyId;

    public UsersHistory() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public UsersRoles getUsersRolesId() {
        return usersRolesId;
    }

    public void setUsersRolesId(UsersRoles usersRolesId) {
        this.usersRolesId = usersRolesId;
    }

    public History getHistoryId() {
        return historyId;
    }

    public void setHistoryId(History historyId) {
        this.historyId = historyId;
    }

    @Override
    public String toString() {
        return "UsersHistory{" +
                "id=" + id +
                ", usersRolesId=" + usersRolesId +
                ", historyId=" + historyId +
                '}';
    }
}
