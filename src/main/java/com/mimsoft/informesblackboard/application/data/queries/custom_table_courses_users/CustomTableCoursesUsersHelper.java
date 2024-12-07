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
        try {
            return data.get(column).getOrDefault(row, 0);
        } catch (Exception ignore) {
            return 0;
        }
    }

    public Integer getTotalColumn(String key1) {
        Integer total = 0;
        for (String key2 : getAllRows()) {
            total += getValue(key1, key2);
        }
        return total;
    }

    public Integer getTotalRow(String key2) {
        Integer total = 0;
        for (String key1 : getAllColumns()) {
            total += getValue(key1, key2);
        }
        return total;
    }

    public Integer[] getTotalAllColumn() {
        List<String> columns = getAllColumns();
        Integer[] total = new Integer[columns.size()];
        for (int i = 0; i < total.length; i++) {
            total[i] = getTotalColumn(columns.get(i));
        }
        return total;
    }

    public Integer[] getTotalAllRow() {
        List<String> rows = getAllRows();
        Integer[] total = new Integer[rows.size()];
        for (int i = 0; i < total.length; i++) {
            total[i] = getTotalRow(rows.get(i));
        }
        return total;
    }

    public Integer[] getColumnValues(String row) {
        Integer[] values = new Integer[columns.size()];
        int k = 0;
        for (String column : getAllColumns()) {
            values[k] = getValue(column, row);
            k++;
        }
        return values;
    }

    public Integer[] getRowValues(String column) {
        Integer[] values = new Integer[rows.size()];
        int k = 0;
        for (String row : getAllRows()) {
            try {
                values[k] = getValue(column, row);
                k++;
            } catch (Exception e) {
                System.out.println("Error en el indice >> CustomTableCoursesUsersHelper::getRowValues");
                e.printStackTrace();
            }
        }
        return values;
    }

    public Integer[][] getValuesColRow() {
        Integer[][] values = new Integer[columns.size()][rows.size()];
        List<String> rows = getAllRows();
        List<String> columns = getAllColumns();
        for (int col = 0; col < columns.size(); col++) {
            for (int row = 0; row < rows.size(); row++) {
                values[col][row] = getValue(columns.get(col), rows.get(row));
            }
        }
        return values;
    }

    public Integer getTotal() {
        Integer total = 0;
        for (String key1 : getAllColumns()) {
            for (String key2 : getAllRows()) {
                total += getValue(key1, key2);
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "CustomTableCoursesUsersHelper{" +
                "data=" + data +
                ", columns=" + columns +
                ", rows=" + rows +
                '}';
    }
}