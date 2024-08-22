package com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users;

import java.util.*;

public class CustomTableCoursesUsersHelper {
    private final HashMap<String, HashMap<String, Integer>> data;
    private final Set<String> columns;
    private final Set<String> rows;

    public CustomTableCoursesUsersHelper() {
        data = new LinkedHashMap<>();
        columns = new LinkedHashSet<>();
        rows = new LinkedHashSet<>();
    }

    public boolean render() {
        return !columns.isEmpty() && !rows.isEmpty();
    }

    public void add(String column, String row, int value) {
        if (column == null || row == null) return;

        if (data.get(column) == null) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put(row, value);
            data.put(column, map);
        } else {
            data.get(column).putIfAbsent(row, value);
        }
        columns.add(column);
        rows.add(row);
    }

    public List<String> getAllColumns() {
        return new ArrayList<>(columns);
    }

    public List<String> getAllRows() {
        return new ArrayList<>(rows);
    }


    public Integer getValue(String column, String row) {
        try { return data.get(column).getOrDefault(row, 0); }
        catch (Exception ignore) { return 0; }
    }

    public Integer getTotalColumn(String key1) {
        Integer total = 0;
        for (String key2: getAllRows()) {
            total += getValue(key1, key2);
        }
        return total;
    }

    public Integer getTotalRow(String key2) {
        Integer total = 0;
        for (String key1: getAllColumns()) {
            total += getValue(key1, key2);
        }
        return total;
    }

    public Integer[] getColumnValues(String row) {
        Integer[] values = new Integer[rows.size()];
        int k = 0;
        for (String column: getAllColumns()) {
            values[k] = getValue(column, row);
            k++;
        }
        return values;
    }

    public Integer[] getRowValues(String column) {
        Integer[] values = new Integer[columns.size()];
        int k = 0;
        for (String row: getAllRows()) {
            values[k] = getValue(column, row);
            k++;
        }
        return values;
    }

    public Integer getTotal() {
        Integer total = 0;
        for (String key1: getAllColumns()) {
            for (String key2: getAllRows()) {
                total += getValue(key1, key2);
            }
        }
        return total;
    }
}
