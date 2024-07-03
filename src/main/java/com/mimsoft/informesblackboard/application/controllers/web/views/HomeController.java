package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods.CustomPeriodsRepository;
import com.mimsoft.informesblackboard.application.data.repositories.GradesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.StorageHistoryRepository;
import com.mimsoft.informesblackboard.application.modules.graphics.ChartsServices;
import com.mimsoft.informesblackboard.application.modules.graphics.TablesServices;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import com.mimsoft.informesblackboard.domain.entities.StorageHistory;
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
    private TablesServices tablesServices;
    @Inject
    private CustomPeriodsRepository customPeriodsRepository;
    @Inject
    private GradesRepository gradesRepository;
    @Inject
    private StorageHistoryRepository storageHistoryRepository;

    private String[] selectedPeriodUsers;
    private String[] selectedPeriodCourses;

    @Override
    public void init() {
        List<Grades> gradesList = gradesRepository.findAll();
        String result = customPeriodsRepository.findLastString();
        selectedPeriodUsers = new String[gradesList.size()];
        selectedPeriodCourses = new String[gradesList.size()];
        for (int i = 0; i < gradesList.size(); i++) {
            selectedPeriodUsers[i] = result;
            selectedPeriodCourses[i] = result;
        }
    }

    public String getCustomTableCourses(String period, Grades grades) {
        return tablesServices.getCustomPeriodCourses(period, grades);
    }

    public String getCustomTableUsers(String period, Grades grades) {
        return tablesServices.getCustomPeriodUsers(period, grades);
    }

    public String getStorageHistory() {
        List<StorageHistory> list = storageHistoryRepository.findAll();
        if (list.isEmpty()) return chartsServices.getEmptyShowMessage(sessionController.getBundleMessage("empty_graphic"));
        return chartsServices.getStorageHistory(list);
    }

    public List<String> getCoursesPeriod() {
        return customPeriodsRepository.findAllString();
    }

    public List<Grades> getAllGrades() {
        return gradesRepository.findAll();
    }

    public String[] getSelectedPeriodUsers() {
        return selectedPeriodUsers;
    }

    public void setSelectedPeriodUsers(String[] selectedPeriodUsers) {
        this.selectedPeriodUsers = selectedPeriodUsers;
    }

    public String[] getSelectedPeriodCourses() {
        return selectedPeriodCourses;
    }

    public void setSelectedPeriodCourses(String[] selectedPeriodCourses) {
        this.selectedPeriodCourses = selectedPeriodCourses;
    }
}
