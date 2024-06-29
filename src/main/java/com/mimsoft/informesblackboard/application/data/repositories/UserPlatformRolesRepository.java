package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.UserPlatformRoles;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class UserPlatformRolesRepository {
    @Inject
    @RepositoryClass(UserPlatformRoles.class)
    private Repository<UserPlatformRoles> repository;

    public List<UserPlatformRoles> findAll() {
        return repository.findAll();
    }

    public UserPlatformRoles findById(Integer id) {
        return repository.findId(id);
    }
}
