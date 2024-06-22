package com.mimsoft.boilerplate.application.controllers.web.views;

import com.mimsoft.boilerplate.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.boilerplate.domain.core.Repository;
import com.mimsoft.boilerplate.domain.core.RepositoryClass;
import com.mimsoft.boilerplate.domain.entities.Users;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

@Named(value = "profileCtrl")
@ViewScoped
public class ProfileController extends AbstractSessionController {
    @Inject
    @RepositoryClass(Users.class)
    private Repository<Users> usersRepository;

    private Users currenUser;

    @Override
    public void init() {
        sessionController.setCurrentUser(usersRepository.findId(sessionController.getCurrentUser().getId()));
        currenUser = sessionController.getCurrentUser();
    }

    public void createRandomPassword() {
        currenUser.setPassword(commonController.createRandomPassword(6, 2));
    }

    public void updateUser() {
        usersRepository.update(currenUser);
        sessionController.setCurrentUser(usersRepository.findId(sessionController.getCurrentUser().getId()));
        commonController.FacesMessagesInfo("Successful", "Update current user");
        PrimeFaces.current().ajax().update("form_app_header");
    }

    public Users getCurrenUser() {
        return currenUser;
    }

    public void setCurrenUser(Users currenUser) {
        this.currenUser = currenUser;
    }
}
