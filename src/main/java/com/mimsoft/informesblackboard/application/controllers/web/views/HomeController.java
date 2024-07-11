package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.models.helper.CustomTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods.CustomPeriods;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods.CustomPeriodsRepository;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsers;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsersRepository;
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
    @Inject
    private CustomTableCoursesUsersRepository customTableCoursesUsersRepository;

    private String[] selectedPeriodUsers;
    private String[] selectedPeriodCourses;

    @Override
    public void init() {
        List<Grades> gradesList = gradesRepository.findAll();
        selectedPeriodUsers = new String[gradesList.size() + 1];
        selectedPeriodCourses = new String[gradesList.size() + 1];
    }

    public String getCustomTableCourses(String period, Grades grades) {
        String defaultTableStr = "<div style='text-align: center; font-size: 1.35rem; margin: 2rem;'>" + sessionController.getBundleMessage("empty_table") + "</div>";
        if (period == null || period.isEmpty() || grades == null) return defaultTableStr;
        CustomTableCoursesUsersHelper customTableCoursesUsersHelper = new CustomTableCoursesUsersHelper();
        for (CustomTableCoursesUsers item: customTableCoursesUsersRepository.findCourses(period, grades.getId())) {
            customTableCoursesUsersHelper.add(item.getCampusId().getName(), item.getModalityId().getDescription() + " (" + item.getModalityId().getName() + ")", item.getValue());
        }
        if (!customTableCoursesUsersHelper.render()) return defaultTableStr;
        return tablesServices.getCustomPeriodTable(customTableCoursesUsersHelper);
    }

    public String getCustomTableUsers(String period, Grades grades) {
        String defaultTableStr = "<div style='text-align: center; font-size: 1.35rem; margin: 2rem;'>" + sessionController.getBundleMessage("empty_table") + "</div>";
        if (period == null || period.isEmpty() || grades == null) return defaultTableStr;
        CustomTableCoursesUsersHelper customTableCoursesUsersHelper = new CustomTableCoursesUsersHelper();
        for (CustomTableCoursesUsers item: customTableCoursesUsersRepository.findUsers(period, grades.getId())) {
            customTableCoursesUsersHelper.add(item.getCampusId().getName(), item.getRolesId().getName(), item.getValue());
        }
        if (!customTableCoursesUsersHelper.render()) return defaultTableStr;
        return tablesServices.getCustomPeriodTable(customTableCoursesUsersHelper);
    }

    public String getStorageHistory() {
        List<StorageHistory> list = storageHistoryRepository.findAll();
        if (list.isEmpty()) return chartsServices.getEmptyShowMessage(sessionController.getBundleMessage("empty_graphic"));
        return chartsServices.getStorageHistory(list);
    }

    public List<CustomPeriods> getCoursesPeriodUsers() {
        return customPeriodsRepository.findAllForUsers();
    }

    public List<CustomPeriods> getCoursesPeriodCourses() {
        return customPeriodsRepository.findAllForCourses();
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
