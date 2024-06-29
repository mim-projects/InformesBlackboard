package com.mimsoft.informesblackboard.application.modules.charts;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ChartsServices {
    public String getStorageHistory() {
        return "{ " +
                "    xAxis: { " +
                "        type: 'category', " +
                "        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] " +
                "    }, " +
                "    yAxis: { " +
                "        type: 'value' " +
                "    }, " +
                "    series: [{ " +
                "        data: [820, 932, 901, 934, 1290, 1330, 1320], " +
                "        type: 'line' " +
                "    }] " +
                "}";
    }
}
