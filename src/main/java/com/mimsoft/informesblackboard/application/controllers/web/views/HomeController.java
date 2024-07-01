package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods.CustomPeriodsRepository;
import com.mimsoft.informesblackboard.application.modules.charts.ChartsServices;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("homeCtrl")
@ViewScoped
public class HomeController extends AbstractSessionController {
    @Inject
    private ChartsServices chartsServices;
    @Inject
    private CustomPeriodsRepository customPeriodsRepository;

    @Override
    public void init() {

    }

    public String getStorageHistory() {
        return chartsServices.getStorageHistory();
    }

    public List<String> getCoursesPeriod() {
        return customPeriodsRepository.findAllString();
    }
}
