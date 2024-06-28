package com.mimsoft.informesblackboard.application.data.queries.custom_users;

import com.mimsoft.informesblackboard.application.data.queries.QueryRepository;
import jakarta.enterprise.context.RequestScoped;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class CustomUsersRepository extends QueryRepository {
    public List<CustomUsers> findAll() {
        String query = "";
        try { return entityManager.createNativeQuery(query, CustomUsers.class).getResultList(); }
        catch (Exception ignore) { return new ArrayList<>(); }
    }
}
