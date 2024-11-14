package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods_grades_campus.CustomPeriodsGradesCampus;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods_grades_campus.CustomPeriodsGradesCampusRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CoursesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.UsersRepository;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheServices;
import org.primefaces.PrimeFaces;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("periodsCtrl")
@ViewScoped
public class PeriodsController extends AbstractSessionController {
    @Inject
    private CustomPeriodsGradesCampusRepository customPeriodsGradesCampusRepository;
    @Inject
    private CoursesRepository coursesRepository;
    @Inject
    private UsersRepository usersRepository;
    @Inject
    private SimulateCacheServices simulateCacheServices;

    private List<CustomPeriodsGradesCampus> customPeriodsGradesCampusesList;
    private String[] months;
    private String selectedPeriods;
    private String selectedMonth;
    private String selectedType;

    @Override
    public void init() {
        customPeriodsGradesCampusesList = customPeriodsGradesCampusRepository.getAll();
        months = sessionController.getBundleMessage("months").split(",");
    }

    public void cleanCache(CustomPeriodsGradesCampus item) {
        simulateCacheServices.remove(item.getKeywordCache());
    }

    public void removeGroup(String widgetClose, String updateId) {
        for (CustomPeriodsGradesCampus item : customPeriodsGradesCampusesList) {
            if (item.getPeriods().equalsIgnoreCase(selectedPeriods) || selectedPeriods == null) {
                if (String.valueOf(item.getDatedAt().getMonth()).equals(selectedMonth) || selectedMonth == null) {
                    if (item.getTable().equalsIgnoreCase(selectedType) || selectedType == null) {
                        cleanCache(item);
                        if (item.getTable().equalsIgnoreCase("users")) {
                            usersRepository.removeAllByPeriodCampusGrades(item.getPeriods(), item.getDatedAt(), item.getCampusId(), item.getGradesId());
                        } else {
                            coursesRepository.removeAllByPeriodCampusGrades(item.getPeriods(), item.getDatedAt(), item.getCampusId(), item.getGradesId());
                        }
                        PrimeFaces.current().ajax().update(updateId);
                    }
                }
            }
        }

        init();
        PrimeFaces.current().ajax().update(updateId);
        PrimeFaces.current().executeScript("PF('" + widgetClose + "').hide()");
        commonController.FacesMessagesWarn(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("remove_group"));
    }

    public void remove(CustomPeriodsGradesCampus item) {
        cleanCache(item);
        if (item.getTable().equalsIgnoreCase("users")) {
            usersRepository.removeAllByPeriodCampusGrades(item.getPeriods(), item.getDatedAt(), item.getCampusId(), item.getGradesId());
        } else {
            coursesRepository.removeAllByPeriodCampusGrades(item.getPeriods(), item.getDatedAt(), item.getCampusId(), item.getGradesId());
        }
        init();
    }

    public List<String> getAllTables() {
        Set<String> items = new LinkedHashSet<>();
        for (CustomPeriodsGradesCampus item: customPeriodsGradesCampusesList) {
            items.add(item.getTable());
        }
        return new ArrayList<>(items);
    }

    public List<String> getAllGrades() {
        Set<String> items = new LinkedHashSet<>();
        for (CustomPeriodsGradesCampus item: customPeriodsGradesCampusesList) {
            items.add(item.getGradesId().getName());
        }
        return new ArrayList<>(items);
    }

    public List<String> getAllCampus() {
        Set<String> items = new LinkedHashSet<>();
        for (CustomPeriodsGradesCampus item: customPeriodsGradesCampusesList) {
            items.add(item.getCampusId().getName());
        }
        return new ArrayList<>(items);
    }

    public List<String> getAllPeriodsDistinct() {
        Set<String> items = new LinkedHashSet<>();
        for (CustomPeriodsGradesCampus item: customPeriodsGradesCampusesList) {
            items.add(item.getPeriods());
        }
        return new ArrayList<>(items);
    }

    public List<String> getAllMonthsUsed() {
        String[] items = new String[months.length];
        for (CustomPeriodsGradesCampus item: customPeriodsGradesCampusesList) {
            if (item.getPeriods().equalsIgnoreCase(selectedPeriods) || selectedPeriods == null) {
                items[item.getDatedAt().getMonth()] = months[item.getDatedAt().getMonth()];
            }
        }
        List<String> clean = new ArrayList<>();
        for (String item: items) {
            if (item != null && !item.isEmpty()) clean.add(item);
        }
        return clean;
    }

    public String[] getAllMonths() {
        return months;
    }

    public String getMonth(Date date) {
        return months[date.getMonth()];
    }

    public List<CustomPeriodsGradesCampus> getAllCustomPeriodsGradesCampus() {
        return customPeriodsGradesCampusesList;
    }

    public String getSelectedPeriods() {
        return selectedPeriods;
    }

    public void setSelectedPeriods(String selectedPeriods) {
        this.selectedPeriods = selectedPeriods;
    }

    public String getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }
}
