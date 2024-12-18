package com.mimsoft.informesblackboard.application.controllers.web.common;

import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.routes.Route;
import com.mimsoft.informesblackboard.application.routes.Routes;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named("routerCtrl")
@SessionScoped
public class RouterController implements Serializable {
    @Inject
    private RequestController requestController;
    @Inject
    private SessionController sessionController;

    public void navigateFromURLParams() {
        String data = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("route");
        String params = String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("params"));
        navigate(data, params.isEmpty() ?null :params);
    }

    public void navigate(Route route) {
        navigate(route.getName(), null);
    }

    public void navigate(Route route, String params) {
        navigate(route.getName(), params);
    }

    public void navigate(String routeName) {
        navigate(routeName, null);
    }

    public void navigate(String routeName, String params) {
        if (routeName.contains("logout")) {
            sessionController.logout();
            return;
        }

        Route routeCurrent = null;
        for (Routes route: Routes.values()) {
            if (routeName.equalsIgnoreCase(route.getRoute().getName())) {
                routeCurrent = route.getRoute();
                break;
            }
        }

        if (routeCurrent == null || (routeCurrent.getRoles() != null && !sessionController.getCurrentUser().getUserPlatformRolesId().getId().equals(routeCurrent.getRoles().getValue()))) {
            String url = requestController.getContext() + Routes.ERROR_404.getRoute().getUrl();
            try { FacesContext.getCurrentInstance().getExternalContext().redirect(url); }
            catch (IOException e) { e.printStackTrace(); }
            return;
        }

        String url = requestController.getContext() + routeCurrent.getUrl() + (params != null ?"?" + params : "");
        try { FacesContext.getCurrentInstance().getExternalContext().redirect(url); }
        catch (IOException e) { e.printStackTrace(); }
    }
}
