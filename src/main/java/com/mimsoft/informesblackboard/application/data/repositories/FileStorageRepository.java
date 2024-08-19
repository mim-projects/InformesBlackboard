package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.FileStorage;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class FileStorageRepository {
    @Inject
    @RepositoryClass(FileStorage.class)
    private Repository<FileStorage> repository;

    public boolean exists(String name) {
        return repository.findOne("name", "'" + name + "'") != null;
    }

    public void create(FileStorage fileStorage) {
        repository.create(fileStorage);
    }
}
