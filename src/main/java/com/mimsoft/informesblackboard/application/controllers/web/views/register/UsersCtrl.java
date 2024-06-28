package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.models.lazy.UsersLazyDataModel;
import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsers;
import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsersRepository;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;

@Named("usersCtrl")
@ViewScoped
public class UsersCtrl extends AbstractSessionController {
    @Inject
    private CustomUsersRepository customUsersRepository;

    private LazyDataModel<CustomUsers> customUsersLazyDataModel;

    @Override
    public void init() {
        customUsersLazyDataModel = new UsersLazyDataModel(customUsersRepository.findAll());
    }

    public LazyDataModel<CustomUsers> getCustomUsersLazyDataModel() {
        return customUsersLazyDataModel;
    }

    public void setCustomUsersLazyDataModel(LazyDataModel<CustomUsers> customUsersLazyDataModel) {
        this.customUsersLazyDataModel = customUsersLazyDataModel;
    }
}
