package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.models.helper.multi_selector_boolean.MultiSelectorBooleanListHelper;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods.CustomPeriodsRepository;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsersRepository;
import com.mimsoft.informesblackboard.application.data.repositories.ModalityRepository;
import com.mimsoft.informesblackboard.application.data.repositories.StorageHistoryRepository;
import com.mimsoft.informesblackboard.application.modules.graphics.ChartsServices;
import com.mimsoft.informesblackboard.application.modules.graphics.TablesServices;
import com.mimsoft.informesblackboard.domain.entities.Modality;
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
    private StorageHistoryRepository storageHistoryRepository;
    @Inject
    private CustomTableCoursesUsersRepository customTableCoursesUsersRepository;
    @Inject
    private ModalityRepository modalityRepository;

    private String[] allMonths;
    private List<String> allPeriods;
    private String selectedPeriod;
    private String selectedMonth;
    private MultiSelectorBooleanListHelper<Modality> modalityMultiSelectorBooleanListHelper;

    @Override
    public void init() {
        allMonths = sessionController.getBundleMessage("months").split(",");
        applyFilters();
    }

    public void applyFilters() {
        allPeriods = customPeriodsRepository.findAllString();
        modalityMultiSelectorBooleanListHelper = new MultiSelectorBooleanListHelper<>();
        for (Modality modality: modalityRepository.findAll()) {
            modalityMultiSelectorBooleanListHelper.add(modality);
        }
    }

    public boolean invalidateFilters() {
        return selectedPeriod == null || selectedMonth == null || !modalityMultiSelectorBooleanListHelper.containTrue();
    }

    public String getUsersChart(String type) {
//        if (invalidateFilters()) return chartsServices.getEmptyShowMessage(sessionController.getBundleMessage("empty_graphic"));
        return chartsServices.getCustomBarChart(
                new String[] {"Mexicali", "Tijuana", "Ensenada"},
                new String[] {"var(--uabc-green)", "var(--uabc-yellow)", "var(--uabc-blue)"},
                new String[] {"Distancia", "Semipresencial", "Presencial"},
                new String[] {"549, 1372, 10612", "903, 2093, 10808", "233, 616, 5624"}
        );
    }

    public String getCoursesChart(String type) {
//        if (invalidateFilters()) return chartsServices.getEmptyShowMessage(sessionController.getBundleMessage("empty_graphic"));
        return chartsServices.getCustomBarChart(
                new String[] {"Mexicali", "Tijuana", "Ensenada"},
                new String[] {"var(--uabc-green)", "var(--uabc-yellow)", "var(--uabc-blue)"},
                new String[] {"Distancia", "Semipresencial", "Presencial"},
                new String[] {"549, 1372, 10612", "903, 2093, 10808", "233, 616, 5624"}
        );
    }

//    public String getCustomTableCourses(String period, Grades grades) {
//        String defaultTableStr = "<div style='text-align: center; font-size: 1.35rem; margin: 2rem;'>" + sessionController.getBundleMessage("empty_table") + "</div>";
//        if (period == null || period.isEmpty() || grades == null) return defaultTableStr;
//        CustomTableCoursesUsersHelper customTableCoursesUsersHelper = new CustomTableCoursesUsersHelper();
//        for (CustomTableCoursesUsers item: customTableCoursesUsersRepository.findCourses(period, grades.getId())) {
//            customTableCoursesUsersHelper.add(item.getCampusId().getName(), item.getModalityId().getDescription() + " (" + item.getModalityId().getName() + ")", item.getValue());
//        }
//        if (!customTableCoursesUsersHelper.render()) return defaultTableStr;
//        return tablesServices.getCustomPeriodTable(customTableCoursesUsersHelper);
//    }
//
//    public String getCustomTableUsers(String period, Grades grades) {
//        String defaultTableStr = "<div style='text-align: center; font-size: 1.35rem; margin: 2rem;'>" + sessionController.getBundleMessage("empty_table") + "</div>";
//        if (period == null || period.isEmpty() || grades == null) return defaultTableStr;
//        CustomTableCoursesUsersHelper customTableCoursesUsersHelper = new CustomTableCoursesUsersHelper();
//        for (CustomTableCoursesUsers item: customTableCoursesUsersRepository.findUsers(period, grades.getId())) {
//            customTableCoursesUsersHelper.add(item.getCampusId().getName(), item.getRolesId().getName(), item.getValue());
//        }
//        if (!customTableCoursesUsersHelper.render()) return defaultTableStr;
//        return tablesServices.getCustomPeriodTable(customTableCoursesUsersHelper);
//    }

    public String getStorageHistory() {
        List<StorageHistory> list = storageHistoryRepository.findAll();
        if (list.isEmpty()) return chartsServices.getEmptyShowMessage(sessionController.getBundleMessage("empty_graphic"));
        return chartsServices.getStorageHistory(list);
    }

    public List<String> getAllPeriods() {
        return allPeriods;
    }

    public String getSelectedPeriod() {
        return selectedPeriod;
    }

    public void setSelectedPeriod(String selectedPeriod) {
        this.selectedPeriod = selectedPeriod;
    }

    public String[] getAllMonths() {
        return allMonths;
    }

    public String getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public MultiSelectorBooleanListHelper<Modality> getModalityMultiSelectorBooleanListHelper() {
        return modalityMultiSelectorBooleanListHelper;
    }

    public void setModalityMultiSelectorBooleanListHelper(MultiSelectorBooleanListHelper<Modality> modalityMultiSelectorBooleanListHelper) {
        this.modalityMultiSelectorBooleanListHelper = modalityMultiSelectorBooleanListHelper;
    }
}
