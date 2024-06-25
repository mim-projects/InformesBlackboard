package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.UserPlatformRepository;
import com.mimsoft.informesblackboard.domain.entities.UserPlatform;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

@Named("profileCtrl")
@ViewScoped
public class ProfileController extends AbstractSessionController {
    @Inject
    private UserPlatformRepository userPlatformRepository;

    private UserPlatform currenUser;

    @Override
    public void init() {
        sessionController.setCurrentUser(userPlatformRepository.findById(sessionController.getCurrentUser().getId()));
        currenUser = sessionController.getCurrentUser();
    }

    public void createRandomPassword() {
        currenUser.setPassword(commonController.createRandomPassword(6, 2));
    }

    public void updateUser() {
        userPlatformRepository.update(currenUser);
        sessionController.setCurrentUser(userPlatformRepository.findById(sessionController.getCurrentUser().getId()));
        commonController.FacesMessagesInfo("Successful", "Update current user");
        PrimeFaces.current().ajax().update("form_app_header");
    }

    public UserPlatform getCurrenUser() {
        return currenUser;
    }

    public void setCurrenUser(UserPlatform currenUser) {
        this.currenUser = currenUser;
    }
}
