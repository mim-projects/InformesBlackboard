package com.mimsoft.informesblackboard.application.modules.reports.dashboard;

public enum SectionTypes {
    USERS("users"),
    COURSES("courses"),
    ;

    private String value;

    SectionTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
