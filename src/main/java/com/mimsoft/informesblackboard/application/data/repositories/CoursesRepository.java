package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Courses;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CoursesRepository {
    @Inject
    @RepositoryClass(Courses.class)
    private Repository<Courses> repository;

    public Courses findOrCreateOrUpdate(String name, String code) {
        Courses item = findByCodeAndName(code, name);
        if (item != null) return item;

        item = findByCode(code);
        if (item != null) {
            item.setName(name);
            return repository.update(item);
        }

        item = new Courses();
        item.setName(name);
        item.setCode(code);
        return repository.create(item);
    }

    public Courses findByCodeAndName(String code, String name) {
        return repository.findOneWhereEqual(
                new String[] {"code", "name"},
                new Object[]{"'" + code + "'", "'" + name + "'"}
        );
    }

    public Courses findByCode(String code) {
        return repository.findOne("code", "'" + code + "'");
    }
}
