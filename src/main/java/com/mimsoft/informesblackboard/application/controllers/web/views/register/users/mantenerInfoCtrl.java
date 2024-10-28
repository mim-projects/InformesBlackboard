/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mimsoft.informesblackboard.application.controllers.web.views.register.users;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.models.lazy.CustomUserLazyDataModel;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods.CustomPeriodsRepository;
import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsers;
import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsersRepository;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author xboxm
 */
@Named("mantenerCtrl")
@SessionScoped
public class mantenerInfoCtrl extends AbstractSessionController {

    @Inject
    private CustomUsersRepository customUsersRepository;
    @Inject
    private CustomPeriodsRepository periodsRepository;

    private LazyDataModel<CustomUsers> customUsersLazyDataModel;
    private Integer counterFilter;
    private String selectedPeriodId;
    private Integer selectedGradeId;
    private Integer selectedRoleId;
    private Integer selectedCampusId;

    @Override
    public void init() {
        counterFilter = 0;

        selectedPeriodId = periodsRepository.findLastString();
        search(); // Inicia la búsqueda al cargar la sesión
    }

    public void search() {

        List<CustomUsers> list = customUsersRepository.findAllByPeriodGradeRoleCampusIds(selectedPeriodId, selectedGradeId, selectedRoleId, selectedCampusId);
        customUsersLazyDataModel = new CustomUserLazyDataModel(list);
        counterFilter = list.size();
    }

    public LazyDataModel<CustomUsers> getCustomUsersLazyDataModel() {
        return customUsersLazyDataModel;
    }

    public String getSelectedPeriodId() {
        return selectedPeriodId;
    }

    public void setSelectedPeriodId(String selectedPeriodId) {
        this.selectedPeriodId = selectedPeriodId;
    }

    public Integer getCounterFilter() {
        return counterFilter;
    }

    public void setCounterFilter(Integer counterFilter) {
        this.counterFilter = counterFilter;
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
