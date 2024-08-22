package com.mimsoft.informesblackboard.application.modules.graphics;

import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsersHelper;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class TablesServices {
    public String getCustomPeriodTable(CustomTableCoursesUsersHelper customTableCoursesUsersHelper) {
        String head = "<tr><th></th>";
        for (String column: customTableCoursesUsersHelper.getAllColumns()) head += "<th>" + column + "</th>";
        head += "<th></th></tr>";

        String body = "";
        for (String row: customTableCoursesUsersHelper.getAllRows()) {
            body += "<tr>";
            body += "<td>" + row + "</td>";
            for (String column: customTableCoursesUsersHelper.getAllColumns()) {
                body += "<td style='text-align: center'>" + customTableCoursesUsersHelper.getValue(column, row) + "</td>";
            }
            body += "<td style='text-align: center;'>" + customTableCoursesUsersHelper.getTotalRow(row) + "</td></tr>";
        }

        // SubTotal
        body += "<tr><td></td>";
        for (String column: customTableCoursesUsersHelper.getAllColumns()) {
            body += "<td style='text-align: center;'>" + customTableCoursesUsersHelper.getTotalColumn(column) + "</td>";
        }
        body += "<td style='text-align: center; background: var(--text-color); color: var(--surface-ground)'>" + customTableCoursesUsersHelper.getTotal() + "</td></tr>";

        return "<table style='width: 100%; border-spacing: 0'>" +
                "   <thead>" + head + "</thead>" +
                "   <tbody>" + body + "</tbody>" +
                "</table>";
    }
}
