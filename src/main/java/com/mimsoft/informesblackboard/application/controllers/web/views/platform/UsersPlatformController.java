package com.mimsoft.informesblackboard.application.controllers.web.views.platform;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.UserPlatformRepository;
import com.mimsoft.informesblackboard.application.data.repositories.UserPlatformRolesRepository;
import com.mimsoft.informesblackboard.domain.entities.UserPlatform;
import com.mimsoft.informesblackboard.domain.entities.UserPlatformRoles;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

import java.util.List;
import java.util.Objects;

@Named("usersPlatformCtrl")
@ViewScoped
public class UsersPlatformController extends AbstractSessionController {
    @Inject
    private UserPlatformRepository userPlatformRepository;
    @Inject
    private UserPlatformRolesRepository userPlatformRolesRepository;

    private UserPlatform selectedUserPlatform;
    private Integer selectedUserRolesPlatformId;

    @Override
    public void init() {

    }

    public void preCreateOrUpdate(UserPlatform item) {
        if (item == null) {
            selectedUserPlatform = new UserPlatform();
            updateSelectedPassword();
        } else {
            selectedUserPlatform = item;
            selectedUserRolesPlatformId = item.getUserPlatformRolesId().getId();
        }
    }

    public void updateSelectedPassword() {
        selectedUserPlatform.setPassword(commonController.createRandomPassword(7, 1));
    }

    public boolean disableOptions(UserPlatform item) {
        return item.getId().equals(sessionController.getCurrentUser().getId()) || userPlatformRepository.findAll().size() < 2;
    }

    public void remove(UserPlatform item) {
        userPlatformRepository.remove(item);
    }

    public void createAndCloseWidget(String widget, String update) {
        if (selectedUserPlatform.getName().isEmpty() || selectedUserPlatform.getUsername().isEmpty() || selectedUserRolesPlatformId == null) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("empty_data"));
            return;
        }
        if (userPlatformRepository.findByUsername(selectedUserPlatform.getUsername()) != null) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("username_exist"));
            return;
        }
        try {
            selectedUserPlatform.setUserPlatformRolesId(userPlatformRolesRepository.findById(selectedUserRolesPlatformId));
            userPlatformRepository.create(selectedUserPlatform);
            commonController.FacesMessagesInfo(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("create_item"));
            PrimeFaces.current().ajax().update(update);
            PrimeFaces.current().executeScript("PF('" + widget + "').hide()");
        } catch (Exception ignore) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("try_again"));
        }
    }

    public void updateAndCloseWidget(String widget, String update) {
        if (selectedUserPlatform.getName().isEmpty() || selectedUserPlatform.getUsername().isEmpty() || selectedUserRolesPlatformId == null) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("empty_data"));
            return;
        }
        UserPlatform result = userPlatformRepository.findByUsername(selectedUserPlatform.getUsername());
        if (result != null && !Objects.equals(result.getId(), selectedUserPlatform.getId()) && !result.getName().equals(selectedUserPlatform.getName())) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("username_exist"));
            return;
        }
        try {
            selectedUserPlatform.setUserPlatformRolesId(userPlatformRolesRepository.findById(selectedUserRolesPlatformId));
            userPlatformRepository.update(selectedUserPlatform);
            commonController.FacesMessagesInfo(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("update_item"));
            PrimeFaces.current().ajax().update(update);
            PrimeFaces.current().executeScript("PF('" + widget + "').hide()");
        } catch (Exception ignore) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("try_again"));
        }
    }

    public List<UserPlatform> getAllUsersPlatform() {
        return userPlatformRepository.findAll();
    }

    public List<UserPlatformRoles> getAllUserPlatformRoles() {
        return userPlatformRolesRepository.findAll();
    }

    public UserPlatform getSelectedUserPlatform() {
        return selectedUserPlatform;
    }

    public void setSelectedUserPlatform(UserPlatform selectedUserPlatform) {
        this.selectedUserPlatform = selectedUserPlatform;
    }

    public Integer getSelectedUserRolesPlatformId() {
        return selectedUserRolesPlatformId;
    }

    public void setSelectedUserRolesPlatformId(Integer selectedUserRolesPlatformId) {
        this.selectedUserRolesPlatformId = selectedUserRolesPlatformId;
    }
}
