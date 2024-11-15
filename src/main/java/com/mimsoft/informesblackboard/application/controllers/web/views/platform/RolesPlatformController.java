package com.mimsoft.informesblackboard.application.controllers.web.views.platform;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.UserPlatformRolesRepository;
import com.mimsoft.informesblackboard.domain.entities.UserPlatformRoles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("rolesPlatformCtrl")
@ViewScoped
public class RolesPlatformController extends AbstractSessionController {
    @Inject
    private UserPlatformRolesRepository userPlatformRolesRepository;

    private List<UserPlatformRoles> allRolesPlatform;

    @Override
    public void init() {
        allRolesPlatform = userPlatformRolesRepository.findAll();
    }

    public List<UserPlatformRoles> getAllRolesPlatform() {
        return allRolesPlatform;
    }

    public void setAllRolesPlatform(List<UserPlatformRoles> allRolesPlatform) {
        this.allRolesPlatform = allRolesPlatform;
    }
}
