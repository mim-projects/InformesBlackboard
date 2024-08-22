package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.FileStorage;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public List<FileStorage> getAll() {
        return repository.findAll();
    }

    public void remove(FileStorage fileStorage) {
        try {
            FileUtils.forceDelete(new File(fileStorage.getPath()));
            repository.delete(fileStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
