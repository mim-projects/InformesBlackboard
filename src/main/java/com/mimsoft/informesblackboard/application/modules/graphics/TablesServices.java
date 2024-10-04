package com.mimsoft.informesblackboard.application.modules.graphics;

import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsersHelper;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import java.util.List;

@RequestScoped
public class TablesServices {
    @Inject
    private OrderDataServices orderDataServices;

    public String getCustomPeriodTable(CustomTableCoursesUsersHelper customTableCoursesUsersHelper) {
        List<String> orderColumns = orderDataServices.orderColumn(customTableCoursesUsersHelper.getAllColumns());

        String head = "<tr><th></th>";
        for (String column: orderColumns) head += "<th>" + column + "</th>";
        head += "<th></th></tr>";

        String body = "";
        for (String row: orderDataServices.orderRow(customTableCoursesUsersHelper.getAllRows())) {
            body += "<tr>";
            body += "<td>" + row + "</td>";
            for (String column: orderColumns) {
                body += "<td style='text-align: center'>" + customTableCoursesUsersHelper.getValue(column, row) + "</td>";
            }
            body += "<td style='text-align: center;'>" + customTableCoursesUsersHelper.getTotalRow(row) + "</td></tr>";
        }

        // SubTotal
        body += "<tr><td></td>";
        for (String column: orderColumns) {
            body += "<td style='text-align: center;'>" + customTableCoursesUsersHelper.getTotalColumn(column) + "</td>";
        }
        body += "<td style='text-align: center; background: var(--text-color); color: var(--surface-ground)'>" + customTableCoursesUsersHelper.getTotal() + "</td></tr>";

        return "<table style='width: 100%; border-spacing: 0'>" +
                "   <thead>" + head + "</thead>" +
                "   <tbody>" + body + "</tbody>" +
                "</table>";
    }
}
