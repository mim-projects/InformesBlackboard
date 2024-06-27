package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.StorageHistory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class StorageHistoryRepository {
    @Inject
    @RepositoryClass(StorageHistory.class)
    private Repository<StorageHistory> repository;

    public List<StorageHistory> findAll() {
        return repository.findNativeAll("select * from storage_history order by id desc");
    }

    public StorageHistory findKeyword(String keyword) {
        return repository.findOne("keyword", "'" + keyword + "'");
    }

    public void remove(StorageHistory item) {
        repository.delete(item);
    }

    public void update(StorageHistory item) {
        repository.update(item);
    }

    public void create(StorageHistory item) {
        repository.create(item);
    }
}
