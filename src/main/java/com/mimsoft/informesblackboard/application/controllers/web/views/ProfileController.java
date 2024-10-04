package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.UserPlatformRepository;
import com.mimsoft.informesblackboard.domain.entities.UserPlatform;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named("profileCtrl")
@ViewScoped
public class ProfileController extends AbstractSessionController {
    @Inject
    private UserPlatformRepository userPlatformRepository;

    private UserPlatform currenUser;
    private boolean editable;

    @Override
    public void init() {
        sessionController.setCurrentUser(userPlatformRepository.findById(sessionController.getCurrentUser().getId()));
        currenUser = sessionController.getCurrentUser();
        editable = false;
    }

    public void createRandomPassword() {
        currenUser.setPassword(commonController.createRandomPassword(6, 2));
    }

    public void updateUser() {
        editable = false;
        userPlatformRepository.update(currenUser);
        sessionController.setCurrentUser(userPlatformRepository.findById(sessionController.getCurrentUser().getId()));
        commonController.FacesMessagesInfo(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("update_profile_data"));
        PrimeFaces.current().ajax().update("form_app_header");
    }

    public UserPlatform getCurrenUser() {
        return currenUser;
    }

    public void setCurrenUser(UserPlatform currenUser) {
        this.currenUser = currenUser;
    }

    public void toggleEditable() {
        editable = !editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
