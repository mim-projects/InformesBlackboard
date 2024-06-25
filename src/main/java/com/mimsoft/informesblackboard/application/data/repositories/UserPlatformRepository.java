package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.UserPlatform;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class UserPlatformRepository {
    @Inject
    @RepositoryClass(UserPlatform.class)
    private Repository<UserPlatform> repository;

    public UserPlatform findByUsernamePassword(String username, String password) {
        return repository.findOneWhereEqual(
                new String[]{"username", "password"},
                new Object[]{"'" + username + "'", "'" + password + "'"}
        );
    }

    public UserPlatform findById(Integer id) {
        return repository.findId(id);
    }

    public void update(UserPlatform currenUser) {
        repository.update(currenUser);
    }
}
