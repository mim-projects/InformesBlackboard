package com.mimsoft.informesblackboard.application.data.queries.custom_users;

public class CustomUsersHelper {
    public static CustomUsers copy(CustomUsers item, String course) {
        CustomUsers customUsers = new CustomUsers();
        customUsers.setUuid(item.getUuid());
        customUsers.setId(item.getId());
        customUsers.setUserKeyword(item.getUserKeyword());
        customUsers.setCoursesKeyword(course);
        customUsers.setCampus(item.getCampus());
        customUsers.setGrade(item.getGrade());
        customUsers.setModality(item.getModality());
        customUsers.setPeriod(item.getPeriod());
        customUsers.setRole(item.getRole());
        return customUsers;
    }
}
