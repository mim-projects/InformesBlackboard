package com.mimsoft.informesblackboard.application.data.queries.custom_periods;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestScoped
public class CustomPeriodsRepository {
    @Inject
    private EntityManager entityManager;

    public List<String> findAllString() {
        Set<String> list = new HashSet<>();
        for (CustomPeriods item: findAllForUsers()) list.add(item.getName());
        for (CustomPeriods item: findAllForCourses()) list.add(item.getName());
        return new ArrayList<>(list);
    }

    public List<CustomPeriods> findAllForUsers() {
        String query = "select uuid() as uuid, users.periods as name from users group by users.periods";
        try { return entityManager.createNativeQuery(query, CustomPeriods.class).getResultList(); }
        catch (Exception e) { return new ArrayList<>(); }
    }

    public List<CustomPeriods> findAllForCourses() {
        String query = "select uuid() as uuid, courses.periods as name from courses group by courses.periods";
        try { return entityManager.createNativeQuery(query, CustomPeriods.class).getResultList(); }
        catch (Exception e) { return new ArrayList<>(); }
    }
}
