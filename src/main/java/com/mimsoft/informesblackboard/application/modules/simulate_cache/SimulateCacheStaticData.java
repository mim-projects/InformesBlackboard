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
        return SimulateCacheKeywords.CustomTableCourses.getKeyword() + periods + "_" + new SimpleDateFormat("yyyy_MM").format(date) + "_" + grade;
    }

    public static String CreateKeywordCoursesCampusTable(String periods, Date date, int grade, int campus, String table) {
        return CreateKeywordCourses(periods, date, grade) + "_" + campus + "_" + table;
    }

    public static String CreateKeywordUsers(Users users) {
        return CreateKeywordUsers(String.valueOf(users.getPeriods()), users.getDatedAt(), users.getGradesId().getId());
    }

    public static String CreateKeywordUsers(String periods, Date date, int grade) {
        return SimulateCacheKeywords.CustomTableUsers.getKeyword() + periods + "_" + new SimpleDateFormat("yyyy_MM").format(date) + "_" + grade;
    }

    public static String CreateKeywordUsersCampusTable(String periods, Date date, int grade, int campus, String table) {
        return CreateKeywordUsers(periods, date, grade) + "_" + campus + "_" + table;
    }

    public static String CreateKeywordCoursesFilters(String period, int month, int[] modality, Integer gradesId) {
        StringBuilder modalities = new StringBuilder();
        for (Integer integer : modality) modalities.append(integer).append("_");
        if (!modalities.toString().isEmpty()) modalities = new StringBuilder(modalities.substring(0, modalities.length() - 1));
        return SimulateCacheKeywords.CustomTableCourses.getKeyword() + period + "_mt" + month + "_g" + gradesId + "_m" + modalities;
    }

    public static String CreateKeywordUsersFilters(String period, int month, int[] roles, Integer gradesId) {
        StringBuilder rol = new StringBuilder();
        for (Integer integer : roles) rol.append(integer).append("_");
        if (!rol.toString().isEmpty()) rol = new StringBuilder(rol.substring(0, rol.length() - 1));
        return SimulateCacheKeywords.CustomTableUsers.getKeyword() + period + "_mt" + month + "_g" + gradesId + "_r" + rol;
    }
}
