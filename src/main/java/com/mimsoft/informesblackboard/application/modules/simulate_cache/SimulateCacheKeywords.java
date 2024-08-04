package com.mimsoft.informesblackboard.application.modules.simulate_cache;

public enum SimulateCacheKeywords {
    CustomTableCoursesUsersRepositoryFindCourses("custom_table_courses_users_repository_find_courses_"),
    CustomTableCoursesUsersRepositoryFindUsers("custom_table_courses_users_repository_find_users_"),
    ;

    private final String keyword;

    SimulateCacheKeywords(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
