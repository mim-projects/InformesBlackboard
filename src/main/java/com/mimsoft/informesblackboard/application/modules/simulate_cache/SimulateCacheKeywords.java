package com.mimsoft.informesblackboard.application.modules.simulate_cache;

public enum SimulateCacheKeywords {
    CustomTableCourses("custom_table_courses_"),
    CustomTableUsers("custom_table_users_"),
    AllPeriods("all_periods"),
    ;

    private final String keyword;

    SimulateCacheKeywords(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
