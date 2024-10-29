package com.mimsoft.informesblackboard.application.controllers.web.common;

import com.mimsoft.informesblackboard.Configuration;
import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.controllers.web.views.HomeController;
import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.repositories.UserPlatformRepository;
import com.mimsoft.informesblackboard.application.routes.Route;
import com.mimsoft.informesblackboard.application.routes.Routes;
import com.mimsoft.informesblackboard.domain.entities.UserPlatform;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Named("sessionCtrl")
@SessionScoped
public class SessionController implements BundleLanguage, Serializable {
    @Inject
    private RequestController requestController;
    @Inject
    private UserPlatformRepository userPlatformRepository;
    @Inject
    private HomeController homeCtrl;

    private UserPlatform currentUser;

    public void validateSession() {
        if (!isActive()) {
            logout();
            return;
        }
        validateRoute();
        updateTheme(null);
        updateLanguage(null);
    }

    public boolean isActive() {
        return currentUser != null;
    }

    public void setCurrentUser(UserPlatform currentUser) {
        this.currentUser = currentUser;
    }

    public boolean setCurrentUser(String username, String password) {
        UserPlatform currentUser = userPlatformRepository.findByUsernamePassword(username, password);
        setCurrentUser(currentUser);
        return true;
    }

    public UserPlatform getCurrentUser() {
        return currentUser;
    }

    public long getExpiredTime() {
        return Configuration.MILLIS_EXPIRED_TIME_SESSION;
    }

    public String getName() {
        return currentUser == null ? "-" : currentUser.getName().length() > 15 ? currentUser.getName().substring(0, 14) : currentUser.getName();
    }

    public String getUsername() {
        return currentUser == null ? "-" : currentUser.getUsername().length() > 15 ? currentUser.getUsername().substring(0, 14) : currentUser.getUsername();
    }

    public void logout() {
        try {
            currentUser = null;
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect(requestController.getContext() + Routes.LOGOUT.getRoute().getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTheme(String theme) {
        if (theme == null || !Objects.equals(currentUser.getTheme(), theme)) {
            if (theme == null) theme = getCurrentTheme();
            currentUser.setTheme(theme.toLowerCase());
            userPlatformRepository.update(currentUser);
        }
    }

    public void updateLanguage(String lang) {
        if (lang == null || !Objects.equals(currentUser.getLanguage(), lang)) {
            if (lang == null) lang = getCurrentLang();
            currentUser.setLanguage(lang.toLowerCase());
            userPlatformRepository.update(currentUser);
            homeCtrl.clearTablesGraphic();
            FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.forLanguageTag(getCurrentLang().toLowerCase()));
        }
    }

    public String getCurrentLang() {
        return currentUser == null || currentUser.getLanguage() == null ? "en" : currentUser.getLanguage();
    }

    public String getCurrentTheme() {
        return currentUser == null || currentUser.getTheme() == null ? "light" : currentUser.getTheme();
    }

    @Override
    public String getBundleMessage(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle_" + getCurrentLang().toLowerCase());
        return resourceBundle.getString(key);
    }

    public boolean isRol(String currentRol) {
        if (currentUser == null || currentUser.getUserPlatformRolesId() == null || currentUser.getUserPlatformRolesId().getName() == null) {
            return false;
        }
        return currentUser.getUserPlatformRolesId().getName().equalsIgnoreCase(currentRol);
    }

    public void validateRoute() {
        String url = requestController.getURL();
        String[] parts = url.split("/app/");

        if (parts.length < 2) logout();
        String[] subParts = parts[1].split("\\.xhtml");

        if (subParts.length < 1) logout();
        String route = "/app/" + subParts[0] + ".xhtml";

        Route currentRoute = null;
        for (Routes current : Routes.values()) {
            if (current.getRoute().getUrl().equals(route)) {
                currentRoute = current.getRoute();
                break;
            }
        }

        if (currentRoute == null) {
            redirectErrorPage();
            return;
        }
        if (currentRoute.getRoles() == null) {
            return;
        }
        if (currentRoute.getRoles().getValue() != currentUser.getUserPlatformRolesId().getId()) {
            redirectErrorPage();
        }
    }

    private void redirectErrorPage() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(requestController.getContext() + Routes.ERROR_404.getRoute().getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}