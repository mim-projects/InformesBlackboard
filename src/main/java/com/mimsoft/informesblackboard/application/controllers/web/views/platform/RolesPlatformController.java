package com.mimsoft.informesblackboard.application.controllers.web.views.platform;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.UserPlatformRolesRepository;
import com.mimsoft.informesblackboard.domain.entities.UserPlatformRoles;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("rolesPlatformCtrl")
@ViewScoped
public class RolesPlatformController extends AbstractSessionController {
    @Inject
    private UserPlatformRolesRepository userPlatformRolesRepository;

    @Override
    public void init() {

    }

    public List<UserPlatformRoles> getAllRolesPlatform() {
        return userPlatformRolesRepository.findAll();
    }
}
