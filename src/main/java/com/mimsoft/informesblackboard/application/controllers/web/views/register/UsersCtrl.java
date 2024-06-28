package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.models.lazy.UsersLazyDataModel;
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
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;

import java.util.List;

@Named("usersCtrl")
@ViewScoped
public class UsersCtrl extends AbstractSessionController {
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
    private Integer counterFilter;

    @Override
    public void init() {
        List<CustomUsers> list = customUsersRepository.findAll();
        customUsersLazyDataModel = new UsersLazyDataModel(list);
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
}
