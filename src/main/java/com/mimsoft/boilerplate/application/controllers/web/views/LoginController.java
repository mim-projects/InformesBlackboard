package com.mimsoft.boilerplate.application.controllers.web.views;

import com.mimsoft.boilerplate.application.controllers.web.common.CommonController;
import com.mimsoft.boilerplate.application.controllers.web.common.RouterController;
import com.mimsoft.boilerplate.application.controllers.web.common.SessionController;
import com.mimsoft.boilerplate.application.routes.Routes;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("loginCtrl")
@ViewScoped
public class LoginController implements Serializable {
    @Inject
    private SessionController sessionController;
    @Inject
    private CommonController commonController;
    @Inject
    private RouterController routerController;

    private String username;
    private String password;

    @PostConstruct
    public void init() {
        validateSession();
    }

    public void validateSession() {
        if (sessionController.isActive()) {
            routerController.navigate(Routes.HOME.name(), null);
        }
    }

    public void login() {
        if (username.isEmpty()) {
            commonController.FacesMessagesError("Failed login", "Username is empty");
            return;
        }
        if (password.isEmpty()) {
            commonController.FacesMessagesError("Failed login", "Password is empty");
            return;
        }

        if (!sessionController.setCurrentUser(username, password)) {
            commonController.FacesMessagesError("Failed login", "User or password is invalid");
            return;
        }

        routerController.navigate(Routes.HOME.name(), null);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
