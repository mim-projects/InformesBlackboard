package com.mimsoft.informesblackboard.application.controllers.web.views.register.users;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.models.lazy.CustomUserLazyDataModel;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods.CustomPeriodsRepository;
import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsers;
import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsersRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CampusRepository;
import com.mimsoft.informesblackboard.application.data.repositories.GradesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.ModalityRepository;
import com.mimsoft.informesblackboard.application.data.repositories.RolesRepository;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import com.mimsoft.informesblackboard.domain.entities.Modality;
import com.mimsoft.informesblackboard.domain.entities.Roles;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;

import java.util.List;

@Named("usersCtrl")
@ViewScoped
public class UsersController extends AbstractSessionController {
    @Inject
    private CustomUsersRepository customUsersRepository;
    @Inject
    private GradesRepository gradesRepository;
    @Inject
    private ModalityRepository modalityRepository;
    @Inject
    private CampusRepository campusRepository;
    @Inject
    private CustomPeriodsRepository periodsRepository;
    @Inject
    private RolesRepository rolesRepository;

    private LazyDataModel<CustomUsers> customUsersLazyDataModel;
    private String selectedPeriodId;
    private Integer selectedGradeId;
    private Integer selectedRoleId;
    private Integer selectedCampusId;
    private Integer counterFilter;

    @Override
    public void init() {
        counterFilter = 0;
        selectedPeriodId = periodsRepository.findLastString();
    }

    public boolean isDisabledSearch() {
        return selectedPeriodId == null || selectedPeriodId.isEmpty();
    }

    public void search() {
        List<CustomUsers> list = customUsersRepository.findAllByPeriodGradeRoleCampusIds(selectedPeriodId, selectedGradeId, selectedRoleId, selectedCampusId);
        customUsersLazyDataModel = new CustomUserLazyDataModel(list);
        counterFilter = list.size();
    }

    public void updateCounter() {
        counterFilter = customUsersLazyDataModel.getRowCount();
    }

    public LazyDataModel<CustomUsers> getCustomUsersLazyDataModel() {
        return customUsersLazyDataModel;
    }

    public void setCustomUsersLazyDataModel(LazyDataModel<CustomUsers> customUsersLazyDataModel) {
        this.customUsersLazyDataModel = customUsersLazyDataModel;
    }

    public Integer getCounterFilter() {
        return counterFilter;
    }

    public void setCounterFilter(Integer counterFilter) {
        this.counterFilter = counterFilter;
    }

    public List<String> getAllPeriodsString() {
        return periodsRepository.findAllString();
    }

    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    public List<Modality> getAllModalities() {
        return modalityRepository.findAll();
    }

    public List<Campus> getAllCampus() {
        return campusRepository.findAll();
    }

    public List<Grades> getAllGrades() {
        return gradesRepository.findAll();
    }

    public String getSelectedPeriodId() {
        return selectedPeriodId;
    }

    public void setSelectedPeriodId(String selectedPeriodId) {
        this.selectedPeriodId = selectedPeriodId;
    }

    public Integer getSelectedGradeId() {
        return selectedGradeId;
    }

    public void setSelectedGradeId(Integer selectedGradeId) {
        this.selectedGradeId = selectedGradeId;
    }

    public Integer getSelectedRoleId() {
        return selectedRoleId;
    }

    public void setSelectedRoleId(Integer selectedRoleId) {
        this.selectedRoleId = selectedRoleId;
    }

    public Integer getSelectedCampusId() {
        return selectedCampusId;
    }

    public void setSelectedCampusId(Integer selectedCampusId) {
        this.selectedCampusId = selectedCampusId;
    }
}
