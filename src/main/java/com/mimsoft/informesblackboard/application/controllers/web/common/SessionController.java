package com.mimsoft.informesblackboard.application.controllers.web.common;

import com.mimsoft.informesblackboard.Configuration;
import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.routes.Routes;
import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Users;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@Named("sessionCtrl")
@SessionScoped
public class SessionController implements Serializable {
    @Inject
    private RequestController requestController;
    @Inject
    @RepositoryClass(Users.class)
    private Repository<Users> usersRepository;

    private Users currentUser;
    private String currentLang;
    private String currentTheme;

    public void validateSession() {
        updateTheme(currentTheme);
        updateLanguage(currentLang);
        if (!isActive()) logout();
    }

    public boolean isActive() {
        return currentUser != null;
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    public boolean setCurrentUser(String username, String password) {
        Users currentUser = usersRepository.findOneWhereEqual(
                new String[]{"username", "password"},
                new Object[]{"'" + username + "'", "'" + password + "'"}
        );
        if (currentUser == null) return false;
        setCurrentUser(currentUser);
        return true;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public long getExpiredTime() {
        return Configuration.MillisExpiredTimeSession;
    }

    public String getName() {
        if (currentUser == null) return "-";
        return currentUser.getName().length() > 15 ?currentUser.getName().substring(0, 14) :currentUser.getName();
    }

    public String getUsername() {
        if (currentUser == null) return "-";
        return currentUser.getUsername().length() > 15 ?currentUser.getUsername().substring(0, 14) :currentUser.getUsername();
    }

    public String getImageThumbnailsProfile() {
        return "https://rickandmortyapi.com/api/character/avatar/" + currentUser.getId() + ".jpeg";
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
        boolean reload = !Objects.equals(currentTheme, theme);
        currentTheme = (theme == null && currentTheme == null) ? "light" :theme;
        if (!currentTheme.equals("dark") && !currentTheme.equals("light")) currentTheme = "light";
        if (reload) PrimeFaces.current().executeScript("location.reload()");
    }

    public void updateLanguage(String lang) {
        boolean reload = !Objects.equals(currentLang, lang);
        currentLang = (lang == null && currentLang == null) ? "EN" :lang;
        FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.forLanguageTag(currentLang.toLowerCase()));
        if (reload) PrimeFaces.current().executeScript("location.reload()");
    }

    public String getCurrentLang() {
        return currentLang;
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public String getBundleMessage(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle_" + currentLang.toLowerCase());
        return resourceBundle.getString(key);
    }
}
