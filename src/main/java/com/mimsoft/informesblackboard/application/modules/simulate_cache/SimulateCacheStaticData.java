package com.mimsoft.informesblackboard.application.modules.simulate_cache;

import com.mimsoft.informesblackboard.domain.entities.Courses;
import com.mimsoft.informesblackboard.domain.entities.Users;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulateCacheStaticData {
    public static String CreateKeywordCourses(Courses courses) {
        return CreateKeywordCourses(String.valueOf(courses.getPeriods()), courses.getDatedAt(), courses.getGradesId().getId());
    }

    public static String CreateKeywordCourses(String periods, Date date, int grade) {
        return SimulateCacheKeywords.CustomTableCoursesUsersRepositoryFindCourses.getKeyword() + periods + "_" + new SimpleDateFormat("yyyy_MM").format(date) + "_" + grade;
    }

    public static String CreateKeywordCoursesCampusTable(String periods, Date date, int grade, int campus, String table) {
        return CreateKeywordCourses(periods, date, grade) + "_" + campus + "_" + table;
    }

    public static String CreateKeywordUsers(Users users) {
        return CreateKeywordUsers(String.valueOf(users.getPeriods()), users.getDatedAt(), users.getGradesId().getId());
    }

    public static String CreateKeywordUsers(String periods, Date date, int grade) {
        return SimulateCacheKeywords.CustomTableCoursesUsersRepositoryFindUsers.getKeyword() + periods + "_" + new SimpleDateFormat("yyyy_MM").format(date) + "_" + grade;
    }

    public static String CreateKeywordUsersCampusTable(String periods, Date date, int grade, int campus, String table) {
        return CreateKeywordUsers(periods, date, grade) + "_" + campus + "_" + table;
    }
}
