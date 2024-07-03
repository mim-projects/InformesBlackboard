package com.mimsoft.informesblackboard.application.modules.graphics;

import com.mimsoft.informesblackboard.domain.entities.StorageHistory;
import jakarta.enterprise.context.RequestScoped;

import java.text.SimpleDateFormat;
import java.util.List;

@RequestScoped
public class ChartsServices {
    public String getEmptyShowMessage(String message) {
        return "{ grid: { left: '2%', right: '2%', top: '2%', bottom: '2%' }, title: { text: \"" + message + "\", show: true, left: \"center\", top: \"center\", textStyle: { fontWeight: \"normal\" } } }";
    }

    public String getStorageHistory(List<StorageHistory> list) {
        String axisX = "";
        String data = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        for (StorageHistory item: list) {
            axisX += "'" + sdf.format(item.getCreatedAt()) + "',";
            data += "'" + item.getValue() + "',";
        }

        if (!data.isEmpty()) {
            axisX = axisX.substring(0, axisX.length() - 1);
            data = data.substring(0, data.length() - 1);
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
                "      areaStyle: {} " +
                "    } " +
                "  ] " +
                "}";
    }
}
