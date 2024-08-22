package com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users;

import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsers;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.modules.graphics.ChartsServices;
import com.mimsoft.informesblackboard.application.modules.graphics.TablesServices;
import com.mimsoft.informesblackboard.domain.entities.Grades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class GraphicTableCoursesUsersHelper {
    private final TablesServices tablesServices;
    private final ChartsServices chartsServices;

    private final HashMap<Integer, String> tableUsers;
    private final HashMap<Integer, String> graphicUsers;
    private final HashMap<Integer, String> tableCourses;
    private final HashMap<Integer, String> graphicCourses;

    List<CustomTableCoursesUsers> usersAllData;
    List<CustomTableCoursesUsers> coursesAllData;

    private final String tableEmpty;
    private final String graphicEmpty;

    public GraphicTableCoursesUsersHelper(BundleLanguage bundleLanguage, TablesServices tablesServices, ChartsServices chartsServices, List<Grades> grades) {
        this.tablesServices = tablesServices;
        this.chartsServices = chartsServices;

        tableUsers = new HashMap<>();
        graphicUsers = new HashMap<>();
        tableCourses = new HashMap<>();
        graphicCourses = new HashMap<>();

        for (Grades grade : grades) {
            tableUsers.put(grade.getId(), null);
            graphicUsers.put(grade.getId(), null);
            tableCourses.put(grade.getId(), null);
            graphicCourses.put(grade.getId(), null);
        }

        // All
        tableUsers.put(0, null);
        graphicUsers.put(0, null);
        tableCourses.put(0, null);
        graphicCourses.put(0, null);

        usersAllData = new ArrayList<>();
        coursesAllData = new ArrayList<>();

        this.tableEmpty = "<div style='height: 100%; width: 100%; display: flex; align-items: center; justify-content: center; font-size: 1.35rem;'>" + bundleLanguage.getBundleMessage("empty_table") + "</div>";
        this.graphicEmpty = "{ grid: { left: '2%', right: '2%', top: '2%', bottom: '2%' }, title: { text: \"" + bundleLanguage.getBundleMessage("empty_graphic") + "\", show: true, left: \"center\", top: \"center\", textStyle: { fontWeight: \"normal\", color: \"var(--text-color)\" } } }";
    }

    public void createTableAll() {
        // Users
        HashMap<String, CustomTableCoursesUsers> usersHashMap = new LinkedHashMap<>();
        for (CustomTableCoursesUsers item : usersAllData) {
            String keyword = "C" + item.getCampusId().getId() + "R" + item.getRolesId().getId();
            CustomTableCoursesUsers exist = usersHashMap.getOrDefault(keyword, null);
            if (exist == null) usersHashMap.put(keyword, item);
            else exist.setValue(exist.getValue() + item.getValue());
        }
        createTableAndGraphic(null, new ArrayList<>(usersHashMap.values()), tableUsers, graphicUsers);

        // Courses
        HashMap<String, CustomTableCoursesUsers> coursesHashMap = new LinkedHashMap<>();
        for (CustomTableCoursesUsers item : coursesAllData) {
            String keyword = "C" + item.getCampusId().getId() + "M" + item.getModalityId().getId();
            CustomTableCoursesUsers exist = coursesHashMap.getOrDefault(keyword, null);
            if (exist == null) coursesHashMap.put(keyword, item);
            else exist.setValue(exist.getValue() + item.getValue());
        }
        createTableAndGraphic(null, new ArrayList<>(coursesHashMap.values()), tableCourses, graphicCourses);
    }

    public void addDataUsers(Grades grades, List<CustomTableCoursesUsers> data) {
        createTableAndGraphic(grades, data, tableUsers, graphicUsers);
        usersAllData.addAll(data);
    }

    public void addDataCourses(Grades grades, List<CustomTableCoursesUsers> data) {
        createTableAndGraphic(grades, data, tableCourses, graphicCourses);
        coursesAllData.addAll(data);
    }

    public String getUserDataFormatString(String type, Integer gradeId) {
        return getString(type, gradeId, tableUsers, graphicUsers);
    }

    public String getCourseDataFormatString(String type, Integer gradeId) {
        return getString(type, gradeId, tableCourses, graphicCourses);
    }

    private void createTableAndGraphic(Grades grade, List<CustomTableCoursesUsers> data, HashMap<Integer, String> tableMap, HashMap<Integer, String> graphicMap) {
        if (data.isEmpty()) return;
        CustomTableCoursesUsersHelper table = new CustomTableCoursesUsersHelper();
        for (CustomTableCoursesUsers current : data) {
            String col = current.getCampusId().getName();
            String row = current.getModalityId() == null
                    ? current.getRolesId().getName()
                    : current.getModalityId().getDescription() + " (" + current.getModalityId().getName() + ")";
            table.add(col, row, current.getValue());
        }
        tableMap.replace(grade == null ? 0 : grade.getId(), tablesServices.getCustomPeriodTable(table));
        graphicMap.replace(grade == null ? 0 : grade.getId(), chartsServices.getCustomBarChart(table));
    }

    private String getString(String type, Integer gradeId, HashMap<Integer, String> table, HashMap<Integer, String> graphic) {
        if (type.equalsIgnoreCase("table")) {
            String result = table.get(gradeId);
            return result == null ? tableEmpty : result;
        } else {
            String result = graphic.get(gradeId);
            return result == null ? graphicEmpty : result;
        }
    }
}
