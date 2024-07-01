package com.mimsoft.informesblackboard.application.modules.charts;

import com.mimsoft.informesblackboard.application.data.repositories.StorageHistoryRepository;
import com.mimsoft.informesblackboard.domain.entities.StorageHistory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.text.SimpleDateFormat;

@RequestScoped
public class ChartsServices {
    @Inject
    private StorageHistoryRepository storageHistoryRepository;

    public String getStorageHistory() {
        String axisX = "";
        String data = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        for (StorageHistory item: storageHistoryRepository.findAll()) {
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
