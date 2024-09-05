package com.mimsoft.informesblackboard.application.modules.reports.utils;

public enum SectionTypes {
    USERS("users"),
    COURSES("courses"),
    ;

    private final String value;

    SectionTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
