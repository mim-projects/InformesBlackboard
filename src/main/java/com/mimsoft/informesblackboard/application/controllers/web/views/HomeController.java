package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.modules.charts.ChartsServices;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("homeCtrl")
@ViewScoped
public class HomeController extends AbstractSessionController {
    @Inject
    private ChartsServices chartsServices;

    @Override
    public void init() {

    }

    public String getStorageHistory() {
        return chartsServices.getStorageHistory();
    }
}
