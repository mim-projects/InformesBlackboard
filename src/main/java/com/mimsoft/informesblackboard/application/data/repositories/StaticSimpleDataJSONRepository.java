package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.StaticSimpleDataJSON;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@RequestScoped
public class StaticSimpleDataJSONRepository {
    @Inject
    @RepositoryClass(StaticSimpleDataJSON.class)
    private Repository<StaticSimpleDataJSON> repository;

    public List<StaticSimpleDataJSON> getAll() {
        return repository.findAll();
    }

    public StaticSimpleDataJSON get(String keyword) {
        return repository.findOne("keyword", "'" + keyword + "'");
    }

    public void put(String keyword, String json, Integer status) {
        StaticSimpleDataJSON result = get(keyword);
        if (result == null) {
            result = new StaticSimpleDataJSON();
            result.setCreatedAt(new Date());
            result.setUpdatedAt(new Date());
            result.setStatus(status);
            result.setKeyword(keyword);
            result.setValue(json);
            repository.create(result);
        } else {
            result.setUpdatedAt(new Date());
            result.setStatus(status);
            result.setValue(json);
            repository.update(result);
        }
    }

    public void status(String keyword, Integer status) {
        StaticSimpleDataJSON result = get(keyword);
        if (result == null) return;
        result.setStatus(status);
        result.setUpdatedAt(new Date());
        repository.update(result);
    }

    public void remove(String keyword) {
        StaticSimpleDataJSON result = get(keyword);
        if (result == null) return;
        repository.delete(result);
    }
}
