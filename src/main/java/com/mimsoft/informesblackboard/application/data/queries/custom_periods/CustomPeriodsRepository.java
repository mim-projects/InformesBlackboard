package com.mimsoft.informesblackboard.application.data.queries.custom_periods;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheKeywords;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheServices;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.*;

@RequestScoped
public class CustomPeriodsRepository {
    @Inject
    private EntityManager entityManager;
    @Inject
    private SimulateCacheServices simulateCacheServices;

    public String findLastString() {
        List<String> list = findAllString();
        if (list.isEmpty()) return null;
        return list.get(list.size() - 1);
    }

    public List<String> findAllString() {
        String periodsCache = simulateCacheServices.get(SimulateCacheKeywords.AllPeriods.getKeyword());
        if (periodsCache == null) {
            Set<String> list = new HashSet<>();
            for (CustomPeriods item: findAllForUsers()) list.add(item.getName());
            for (CustomPeriods item: findAllForCourses()) list.add(item.getName());
            List<String> result = new ArrayList<>(list);
            Collections.sort(result);
            simulateCacheServices.put(SimulateCacheKeywords.AllPeriods.getKeyword(), result);
            periodsCache = simulateCacheServices.get(SimulateCacheKeywords.AllPeriods.getKeyword());
        }
        return new Gson().fromJson(periodsCache, new TypeToken<>() {});
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
