package com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users;

import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsers;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.modules.graphics.ChartsServices;
import com.mimsoft.informesblackboard.application.modules.graphics.TablesServices;
import com.mimsoft.informesblackboard.domain.entities.Grades;

import java.util.*;

public class GraphicTableCoursesUsersHelper {
    private final TablesServices tablesServices;
    private final ChartsServices chartsServices;

    private final HashMap<Integer, String> tableUsers;
    private final HashMap<Integer, String> graphicUsers;
    private final HashMap<Integer, String> tableCourses;
    private final HashMap<Integer, String> graphicCourses;

    private final HashMap<Integer, Grades> gradesList;
    private final List<CustomTableCoursesUsers> usersAllData;
    private final List<CustomTableCoursesUsers> coursesAllData;
    private final HashMap<Grades, CustomTableCoursesUsersHelper> usersAllDataHelper;
    private final HashMap<Grades, CustomTableCoursesUsersHelper> coursesAllDataHelper;

    private final String tableEmpty;
    private final String graphicEmpty;

    public GraphicTableCoursesUsersHelper(BundleLanguage bundleLanguage, TablesServices tablesServices, ChartsServices chartsServices, List<Grades> grades) {
        this.tablesServices = tablesServices;
        this.chartsServices = chartsServices;

        tableUsers = new HashMap<>();
        graphicUsers = new HashMap<>();
        tableCourses = new HashMap<>();
        graphicCourses = new HashMap<>();

        gradesList = new LinkedHashMap<>();
        gradesList.put(0, new Grades(0, bundleLanguage.getBundleMessage("all")));

        for (Grades grade : grades) {
            gradesList.put(grade.getId(), grade);
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
        usersAllDataHelper = new LinkedHashMap<>();
        coursesAllDataHelper = new LinkedHashMap<>();

        for (Grades grade : gradesList.values()) {
            usersAllDataHelper.put(grade, null);
            coursesAllDataHelper.put(grade, null);
        }

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
        createTableAndGraphic(null, new ArrayList<>(usersHashMap.values()), tableUsers, graphicUsers, usersAllDataHelper);

        // Courses
        HashMap<String, CustomTableCoursesUsers> coursesHashMap = new LinkedHashMap<>();
        for (CustomTableCoursesUsers item : coursesAllData) {
            String keyword = "C" + item.getCampusId().getId() + "M" + item.getModalityId().getId();
            CustomTableCoursesUsers exist = coursesHashMap.getOrDefault(keyword, null);
            if (exist == null) coursesHashMap.put(keyword, item);
            else exist.setValue(exist.getValue() + item.getValue());
        }
        createTableAndGraphic(null, new ArrayList<>(coursesHashMap.values()), tableCourses, graphicCourses, coursesAllDataHelper);
    }

    public void addDataUsers(Grades grades, List<CustomTableCoursesUsers> data) {
        createTableAndGraphic(grades, data, tableUsers, graphicUsers, usersAllDataHelper);
        usersAllData.addAll(data);
    }

    public void addDataCourses(Grades grades, List<CustomTableCoursesUsers> data) {
        createTableAndGraphic(grades, data, tableCourses, graphicCourses, coursesAllDataHelper);
        coursesAllData.addAll(data);
    }

    public String getUserDataFormatString(String type, Integer gradeId) {
        return getString(type, gradeId, tableUsers, graphicUsers);
    }

    public String getCourseDataFormatString(String type, Integer gradeId) {
        return getString(type, gradeId, tableCourses, graphicCourses);
    }

    private void createTableAndGraphic(Grades grade, List<CustomTableCoursesUsers> data, HashMap<Integer, String> tableMap, HashMap<Integer, String> graphicMap, HashMap<Grades, CustomTableCoursesUsersHelper> helperMap) {
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
        // All Helper
        Grades gradeHelper = grade == null ? gradesList.get(0) : grade;
        for (Grades item : helperMap.keySet()) {
            if (item.getId().equals(gradeHelper.getId())) {
                helperMap.put(item, table);
                break;
            }
        }
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

    public boolean containsData() {
        return !usersAllData.isEmpty() || !coursesAllData.isEmpty();
    }

    public boolean renderHelper(String type, Grades grades) {
        Grades helperGrade = grades == null ? gradesList.get(0) : grades;
        return getCustomTableGraphicDataHelper(type, helperGrade) != null;
    }

    public CustomTableCoursesUsersHelper getCustomTableGraphicDataHelper(String type, Grades grades) {
        if (type.equalsIgnoreCase("users")) {
            for (Grades current : usersAllDataHelper.keySet()) {
                if (current.getId().equals(grades.getId())) {
                    return usersAllDataHelper.get(current);
                }
            }
        } else {
            for (Grades current : coursesAllDataHelper.keySet()) {
                if (current.getId().equals(grades.getId())) {
                    return coursesAllDataHelper.get(current);
                }
            }
        }
        return null;
    }

    public List<Grades> getAllGradesForType(String type) {
        Set<Grades> grades = new LinkedHashSet<>();
        if (type.equalsIgnoreCase("users")) {
            for (Integer item: tableUsers.keySet()) {
                grades.add(gradesList.get(item));
            }
        } else {
            for (Integer item: tableCourses.keySet()) {
                grades.add(gradesList.get(item));
            }
        }

        // Order Grades
        Set<Grades> order = new LinkedHashSet<>();
        for (Grades current: grades) {
            if (current.getId() > 0) order.add(current);
        }
        order.add(gradesList.get(0));

        List<Grades> list = new ArrayList<>();
        for (Grades curren: order) {
            for (Grades itemList: grades) {
                if (curren.getId().equals(itemList.getId())) {
                    list.add(curren);
                    break;
                }
            }
        }
        return list;
    }
}
