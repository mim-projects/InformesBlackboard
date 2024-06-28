package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.CampusCodes;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class CampusCodesRepository {
    @Inject
    @RepositoryClass(CampusCodes.class)
    private Repository<CampusCodes> repository;

    public List<CampusCodes> findAll() {
        return repository.findAll();
    }

    public List<CampusCodes> findAllDesc() {
        return repository.findNativeAll("select * from campus_codes order by id desc");
    }

    public void remove(CampusCodes campusCodes) {
        repository.delete(campusCodes);
    }

    public void create(CampusCodes campusCodes) {
        repository.create(campusCodes);
    }

    public CampusCodes findByCode(String code) {
        return repository.findOne("code", "'" + code + "'");
    }
}
