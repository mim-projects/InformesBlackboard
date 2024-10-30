package com.mimsoft.informesblackboard.application.data.queries.custom_keyword_value;

import com.mimsoft.informesblackboard.application.data.queries.QueryRepository;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CustomKeywordValueRepository extends QueryRepository {
    public float getValueSingleQuery(float valueDefault, String query) {
        try {
            CustomKeywordValue result = (CustomKeywordValue) entityManager.createNativeQuery(query, CustomKeywordValue.class).getSingleResult();
            if (result == null) return valueDefault;
            return result.getValue();
        } catch (Exception ignore) {
            return valueDefault;
        }
    }
}
