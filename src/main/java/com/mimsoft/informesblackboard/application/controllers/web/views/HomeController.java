package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.interfaces.DataResourceReports;
import com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users.GraphicTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.data.models.helper.multi_selector_boolean.MultiSelectorBooleanListHelper;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods.CustomPeriodsRepository;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsersRepository;
import com.mimsoft.informesblackboard.application.data.repositories.GradesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.ModalityRepository;
import com.mimsoft.informesblackboard.application.data.repositories.RolesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.StorageHistoryRepository;
import com.mimsoft.informesblackboard.application.modules.graphics.ChartsServices;
import com.mimsoft.informesblackboard.application.modules.graphics.OrderDataServices;
import com.mimsoft.informesblackboard.application.modules.graphics.TablesServices;
import com.mimsoft.informesblackboard.application.modules.reports.DashboardReport;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import com.mimsoft.informesblackboard.domain.entities.Modality;
import com.mimsoft.informesblackboard.domain.entities.Roles;
import com.mimsoft.informesblackboard.domain.entities.StorageHistory;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Named("homeCtrl")
@SessionScoped
public class HomeController extends AbstractSessionController implements DataResourceReports {
    @Inject
    private RequestController requestController;
    @Inject
    private GradesRepository gradesRepository;
    @Inject
    private CustomPeriodsRepository customPeriodsRepository;
    @Inject
    private StorageHistoryRepository storageHistoryRepository;
    @Inject
    private CustomTableCoursesUsersRepository customTableCoursesUsersRepository;
    @Inject
    private ModalityRepository modalityRepository;
    @Inject
    private RolesRepository rolesRepository;
    @Inject
    private TablesServices tablesServices;
    @Inject
    private ChartsServices chartsServices;
    @Inject
    private OrderDataServices orderDataServices;

    private String[] allMonths;
    private List<String> allPeriods;
    private String selectedPeriod;
    private String selectedMonth;
    private MultiSelectorBooleanListHelper<Modality> modalityMultiSelectorBooleanListHelper;
    private MultiSelectorBooleanListHelper<Roles> rolesMultiSelectorBooleanListHelper;
    private GraphicTableCoursesUsersHelper graphicTableCoursesUsersHelper;

    @Override
    public void init() {
        allMonths = sessionController.getBundleMessage("months").split(",");
        clearFilter();
    }

    public void applyFilters() {
        List<Modality> modalitiesSelected = modalityMultiSelectorBooleanListHelper.getSelected();
        int[] modalities = new int[modalitiesSelected.size()];
        for (int i = 0; i < modalitiesSelected.size(); i++) modalities[i] = modalitiesSelected.get(i).getId();

        List<Roles> rolesSelected = rolesMultiSelectorBooleanListHelper.getSelected();
        int[] roles = new int[rolesSelected.size()];
        for (int i = 0; i < rolesSelected.size(); i++) roles[i] = rolesSelected.get(i).getId();

        int indexMonth = -1;
        for (int i = 0; i < allMonths.length; i++) {
            if (allMonths[i].equals(selectedMonth)) {
                indexMonth = i + 1;
                break;
            }
        }

        clearTablesGraphic();
        for (Grades grade: gradesRepository.findAll()) {
            graphicTableCoursesUsersHelper.addDataUsers(grade, customTableCoursesUsersRepository.findUsers(selectedPeriod, indexMonth, roles, grade.getId()));
            graphicTableCoursesUsersHelper.addDataCourses(grade, customTableCoursesUsersRepository.findCourses(selectedPeriod, indexMonth, modalities, grade.getId()));
        }
        graphicTableCoursesUsersHelper.createTableAll();
    }

    public void clearFilter() {
        modalityMultiSelectorBooleanListHelper = new MultiSelectorBooleanListHelper<>();
        rolesMultiSelectorBooleanListHelper = new MultiSelectorBooleanListHelper<>();

        for (Modality modality: modalityRepository.findAll()) {
            modalityMultiSelectorBooleanListHelper.add(modality);
        }

        for (Roles roles: rolesRepository.findAll()) {
            rolesMultiSelectorBooleanListHelper.add(roles);
        }

        clearTablesGraphic();
    }

    public void clearTablesGraphic() {
        List<Grades> grades = gradesRepository.findAll();
        allPeriods = customPeriodsRepository.findAllString();
        graphicTableCoursesUsersHelper = new GraphicTableCoursesUsersHelper(sessionController, tablesServices, chartsServices, grades);

        int indexMonth = -1;
        for (int i = 0; i < allMonths.length; i++) {
            if (allMonths[i].equals(selectedMonth)) {
                indexMonth = i;
                break;
            }
        }
        allMonths = sessionController.getBundleMessage("months").split(",");
        selectedMonth = indexMonth == -1 ? null : allMonths[indexMonth];
    }

    public boolean invalidateFilters() {
        return selectedPeriod == null || selectedMonth == null
                || !modalityMultiSelectorBooleanListHelper.containTrue()
                || !rolesMultiSelectorBooleanListHelper.containTrue();
    }

    private String createFilename() {
        int indexMonth = 0;
        for (int i = 0; i < allMonths.length; i++) {
            if (allMonths[i].equals(selectedMonth)) {
                indexMonth = i + 1;
                break;
            }
        }
        String month = indexMonth < 10 ? "0" + indexMonth : "" + indexMonth;
        month = selectedPeriod.substring(0, 4) + "-" + month;
        return month + " - IGUPBB - " + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public void createReportPDF() {
        try {
            String periodLegend = selectedPeriod.length() > 4
                    ? (selectedPeriod.substring(0, 4) + "-" + selectedPeriod.substring(4))
                    : selectedPeriod;
            periodLegend += " | " + selectedMonth;
            DashboardReport.DownloadPDF(this, createFilename(), periodLegend);
        } catch (Exception e) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("try_again"));
        }
    }

    public void createReportSpreadSheet() {
        try {
            String periodLegend = selectedPeriod.length() > 4
                    ? (selectedPeriod.substring(0, 4) + "-" + selectedPeriod.substring(4))
                    : selectedPeriod;
            periodLegend += " | " + selectedMonth;
            DashboardReport.DownloadSpreadsheet(this, createFilename(), periodLegend);
        } catch (Exception e) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("try_again"));
        }
    }

    public boolean disabledButtonReport() {
        return invalidateFilters() || !graphicTableCoursesUsersHelper.containsData();
    }

    public String getTableOrGraphic(String userOrCourse, String type, Integer gradeId) {
        if (userOrCourse.equalsIgnoreCase("users")) return graphicTableCoursesUsersHelper.getUserDataFormatString(type, gradeId);
        else return graphicTableCoursesUsersHelper.getCourseDataFormatString(type, gradeId);
    }

    public String getStorageHistory() {
        List<StorageHistory> list = new ArrayList<>(getAllStorageHistory());
        Collections.reverse(list);
        if (list.isEmpty()) return chartsServices.getEmptyShowMessage(sessionController.getBundleMessage("empty_graphic"));
        return chartsServices.getStorageHistory(list);
    }

    public List<String> getAllPeriods() {
        return allPeriods;
    }

    public List<Grades> getAllGradesForUI() {
        List<Grades> grades = gradesRepository.findAll();
        Grades all = new Grades();
        all.setId(0);
        all.setName(sessionController.getBundleMessage("all"));
        grades.add(all);
        return grades;
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

    public MultiSelectorBooleanListHelper<Roles> getRolesMultiSelectorBooleanListHelper() {
        return rolesMultiSelectorBooleanListHelper;
    }

    public void setRolesMultiSelectorBooleanListHelper(MultiSelectorBooleanListHelper<Roles> rolesMultiSelectorBooleanListHelper) {
        this.rolesMultiSelectorBooleanListHelper = rolesMultiSelectorBooleanListHelper;
    }

    // ========================================================================
    // DataResourceReports
    // ========================================================================

    @Override
    public RequestController getRequestController() {
        return requestController;
    }

    @Override
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Override
    public BundleLanguage getBundleLanguage() {
        return sessionController;
    }

    @Override
    public OrderDataServices getOrderDataServices() {
        return orderDataServices;
    }

    @Override
    public GraphicTableCoursesUsersHelper getGraphicTableCoursesUsersHelper() {
        return graphicTableCoursesUsersHelper;
    }

    @Override
    public List<StorageHistory> getAllStorageHistory() {
        return storageHistoryRepository.findAllDesc();
    }
}
