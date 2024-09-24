package com.mimsoft.informesblackboard.application.modules.graphics;

import com.mimsoft.informesblackboard.application.data.constants.Colors;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.domain.entities.StorageHistory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@RequestScoped
public class ChartsServices {
    @Inject
    private OrderDataServices orderDataServices;

    private static final String[] COLORS = new String[] {
            Colors.UABC_YELLOW.getValue(), Colors.UABC_GREEN.getValue(), Colors.UABC_BLUE.getValue(),
            "#c23531", "#2f4554", "#61a0a8", "#d48265", "#91c7ae", "#749f83", "#ca8622", "#bda29a", "#6e7074", "#546570", "#c4ccd3"
    };

    public String getEmptyShowMessage(String message) {
        return "{ grid: { left: '2%', right: '2%', top: '2%', bottom: '2%' }, title: { text: \"" + message + "\", show: true, left: \"center\", top: \"center\", textStyle: { fontWeight: \"normal\", color: \"var(--text-color)\" } } }";
    }

    public String getStorageHistory(List<StorageHistory> list) {
        StringBuilder axisX = new StringBuilder();
        StringBuilder data = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        for (StorageHistory item: list) {
            axisX.append("'").append(sdf.format(item.getCreatedAt())).append("',");
            data.append("'").append(item.getValue()).append("',");
        }

        if (data.length() > 0) {
            axisX = new StringBuilder(axisX.substring(0, axisX.length() - 1));
            data = new StringBuilder(data.substring(0, data.length() - 1));
        }

        return "{ " +
                "  grid: { left: '0%', right: '0%', top: '2%', bottom: '0%', containLabel: true }, " +
                "  legend: {}, " +
                "  tooltip: { trigger: 'axis' }, " +
                "  xAxis: { " +
                "    data: [" + axisX + "] " +
                "  }, " +
                "  yAxis: { " +
                "    splitLine: { lineStyle: { opacity: 0.4 } } " +
                "  }, " +
                "  series: [ " +
                "    { " +
                "      data: [" + data + "], " +
                "      type: 'line', " +
                "      emphasis: { disabled: true }, " +
                "      areaStyle: { color: \"var(--primary-color)\" }, " +
                "      lineStyle: { color: \"var(--primary-color)\" }, " +
                "      itemStyle: { color: \"var(--primary-color)\" }" +
                "    } " +
                "  ] " +
                "}";
    }

    public String getCustomBarChart(CustomTableCoursesUsersHelper customTableCoursesUsersHelper) {
        List<String> unOrderRows = customTableCoursesUsersHelper.getAllRows();
        String[] columns = orderDataServices.orderColumn(customTableCoursesUsersHelper.getAllColumns()).toArray(new String[0]);
        String[] rows = orderDataServices.orderRow(unOrderRows).toArray(new String[0]);
        String[] values = new String[columns.length];
        String[] colors = new String[columns.length];

        int index = 0;
        for (String current: columns) {
            colors[index] = COLORS[index];

            Integer[] valArr = orderDataServices.orderValueForRow(unOrderRows, customTableCoursesUsersHelper.getRowValues(current)).toArray(new Integer[0]);
            StringBuilder valuesStr = new StringBuilder();
            for (Integer integer : valArr) valuesStr.append(integer).append(",");
            if (valuesStr.length() > 0) values[index] = valuesStr.substring(0, valuesStr.length() - 1);
            values[index] = valuesStr.toString();
            index++;
        }

        return getCustomBarChart(columns, colors, rows, values);
    }

    public String getCustomBarChart(String[] campus, String[] color, String[] modalities, String[] values) {
        StringBuilder modalityHelper = new StringBuilder();
        for (String modality: modalities) modalityHelper.append("'").append(modality).append("',");
        if (modalityHelper.length() > 0) modalityHelper = new StringBuilder(modalityHelper.substring(0, modalityHelper.length() - 1));

        StringBuilder seriesHelper = new StringBuilder();
        for (int i = 0; i < campus.length; i++) seriesHelper.append("{ emphasis: { disabled: true }, name: '").append(campus[i]).append("', type: 'bar', itemStyle: { color: \"").append(color[i]).append("\" }, data: [").append(values[i]).append("] },");
        if (seriesHelper.length() > 0) seriesHelper = new StringBuilder(seriesHelper.substring(0, seriesHelper.length() - 1));

        return "{ " +
                "  tooltip: { trigger: 'axis' }, " +
                "  legend: { data: ['" + campus[0] + "', '" + campus[1] + "', '" + campus[2] + "'], textStyle: { color: \"var(--text-color)\" } }, " +
                "  grid: { containLabel: true, left: 0, right: 0, top: \"15%\", bottom: 0 }, " +
                "  toolbox: { show: true, orient: 'vertical', left: 'right', top: 'top', " +
                "    feature: { " +
                "      mark: { show: true }, " +
                "      magicType: { show: true, type: ['stack'] } " +
                "    } " +
                "  }, " +
                "  xAxis: [ { type: 'category', data: [" + modalityHelper + "], axisLabel: { interval: 0 } } ], " +
                "  yAxis: [ { splitLine: { lineStyle: { color: \"var(--surface-ground)\" } } }, ], " +
                "  series: [" + seriesHelper + "] " +
                "}";
    }
}
