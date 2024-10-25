package com.mimsoft.informesblackboard.application.controllers.web.common;

import com.mimsoft.informesblackboard.Configuration;
import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.repositories.UserPlatformRepository;
import com.mimsoft.informesblackboard.application.routes.Route;
import com.mimsoft.informesblackboard.application.routes.Routes;
import com.mimsoft.informesblackboard.domain.entities.UserPlatform;
import com.sun.source.tree.ContinueTree;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;

@Named("sessionCtrl")
@SessionScoped
public class SessionController implements BundleLanguage, Serializable {

    @Inject
    private RequestController requestController;
    @Inject
    private UserPlatformRepository userPlatformRepository;

    private UserPlatform currentUser;
    private String currentLang;
    private String currentTheme;

    public void validateSession() {
        updateTheme(currentTheme);
        updateLanguage(currentLang==null?"EN":currentLang, false);
        if (!isActive()) {
            logout();
        }
        validateRoute();
    }

    public boolean isActive() {
        return currentUser != null;
    }

    public void setCurrentUser(UserPlatform currentUser) {
        this.currentUser = currentUser;
    }

    public boolean setCurrentUser(String username, String password) {
        UserPlatform currentUser = userPlatformRepository.findByUsernamePassword(username, password);
        if (currentUser == null) {
            return false;
        }
        setCurrentUser(currentUser);
        
        currentLang= currentUser.getLanguage();
        return true;
    }

    public UserPlatform getCurrentUser() {
        return currentUser;
    }

    public long getExpiredTime() {
        return Configuration.MILLIS_EXPIRED_TIME_SESSION;
    }

    public String getName() {
        if (currentUser == null) {
            return "-";
        }
        return currentUser.getName().length() > 15 ? currentUser.getName().substring(0, 14) : currentUser.getName();
    }

    public String getUsername() {
        if (currentUser == null) {
            return "-";
        }
        return currentUser.getUsername().length() > 15 ? currentUser.getUsername().substring(0, 14) : currentUser.getUsername();
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
        System.out.println("--------------------------------------");
        System.out.println("Entré a updateTheme");

        // Obtener el tema actual desde la base de datos si currentTheme es nulo
        if (currentTheme == null) {
            currentTheme = userPlatformRepository.getCurrentTheme(currentUser);
            System.out.println("El tema actual obtenido de la base de datos es: " + currentTheme);
        }

        System.out.println("CurrentTheme: " + currentTheme);

        // Si el tema es light y currentTheme es nulo, se establece el currentTheme desde la base de datos
        if (currentTheme == null && "light".equals(theme)) {
            theme = currentTheme; // Se obtiene de la base de datos
        }

        // Verificar que el tema proporcionado sea válido
        if (theme == null || (!theme.equals("light") && !theme.equals("dark"))) {
            theme = currentTheme; // Establecer un tema por defecto
        }

        System.out.println("Theme: " + theme);

        // Actualizar el tema del usuario actual
        currentUser.setTheme(theme);
        userPlatformRepository.updatetheme(currentUser, theme); // Persistir el cambio en la base de datos

        // Verificar si hay un cambio en el tema para recargar la página
        boolean reload = !Objects.equals(currentTheme, theme);
        currentTheme = theme;

        // Recargar la página si es necesario
        if (reload) {
            PrimeFaces.current().executeScript("location.reload()");
        }
    }

    public void updateLanguage(String lang, Boolean actualizarbasedatos) {
        System.out.println("---------------------------------------------------");
        System.out.println("Entre a update language");
        System.out.println("CurrentUser: " + currentUser);

        System.out.println("Current Language:" + currentLang);
        System.out.println("Lang: " + lang);
        System.out.println("actualizarbasedatos: "+actualizarbasedatos);
        if (actualizarbasedatos) {
            System.out.println("Base de datos update: " + actualizarbasedatos);
            
            String currentLang2 = userPlatformRepository.getCurrentlang(currentUser);

            if (currentLang2 == null) {
                System.out.println("no tiene resultado currentlang");
                currentLang2 = "EN";
            }
            currentLang2=currentLang;
            System.out.println("--------------------------------------------------");
            System.out.println("Current Language: Despues de consulta: " + currentLang2);
            System.out.println("Lang: Despues de consulta:  " + lang);
            System.out.println("--------------------------------------------------");

            currentUser.setLanguage(lang);
            userPlatformRepository.update(currentUser);

      
        } else {
            System.out.println("Base de datos update: " + actualizarbasedatos);

            
            
        }

           System.out.println("--------------------------------------------------");
            System.out.println("Current Language: Despues de consulta: " + currentLang);
            System.out.println("Lang: Despues de consulta:  " + lang);
            System.out.println("--------------------------------------------------");

           boolean reload = !Objects.equals(currentLang, lang);
            currentLang=lang;
            
            FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.forLanguageTag(currentLang.toLowerCase()));

            if (reload) {
                System.out.println("Estoy dentro de reload");
                PrimeFaces.current().executeScript("setTimeout(function(){ window.location.reload(); }, 500);");
            }
    }

    public String getCurrentLang() {
        return currentLang;
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    @Override
    public String getBundleMessage(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle_" + currentLang.toLowerCase());
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

        if (parts.length < 2) {
            logout();
        }
        String[] subParts = parts[1].split("\\.xhtml");

        if (subParts.length < 1) {
            logout();
        }
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
