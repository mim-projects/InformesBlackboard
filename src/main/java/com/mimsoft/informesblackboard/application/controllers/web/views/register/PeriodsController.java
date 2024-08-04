package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods_grades_campus.CustomPeriodsGradesCampus;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods_grades_campus.CustomPeriodsGradesCampusRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CoursesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.UsersRepository;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheKeywords;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheServices;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public void init() {
        customPeriodsGradesCampusesList = customPeriodsGradesCampusRepository.getAll();
    }

    public void cleanCache(CustomPeriodsGradesCampus item) {
        String subKeyword = item.getPeriods() + "_" + item.getGradesId().getId();
        if (item.getTable().equalsIgnoreCase("users")) {
            simulateCacheServices.remove(SimulateCacheKeywords.CustomTableCoursesUsersRepositoryFindUsers.getKeyword() + subKeyword);
        } else {
            simulateCacheServices.remove(SimulateCacheKeywords.CustomTableCoursesUsersRepositoryFindCourses.getKeyword() + subKeyword);
        }
    }

    public void remove(CustomPeriodsGradesCampus item) {
        cleanCache(item);
        if (item.getTable().equalsIgnoreCase("users")) {
            usersRepository.removeAllByPeriodCampusGrades(item.getPeriods(), item.getCampusId(), item.getGradesId());
        } else {
            coursesRepository.removeAllByPeriodCampusGrades(item.getPeriods(), item.getCampusId(), item.getGradesId());
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

    public List<CustomPeriodsGradesCampus> getAllCustomPeriodsGradesCampus() {
        return customPeriodsGradesCampusesList;
    }
}
