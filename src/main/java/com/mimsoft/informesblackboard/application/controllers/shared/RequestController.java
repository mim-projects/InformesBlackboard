package com.mimsoft.informesblackboard.application.controllers.shared;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

@Named("requestCtrl")
@RequestScoped
public class RequestController {
    public String getParam(String key) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return params.get(key);
    }

    public boolean existParam(String key, String value) {
        if (value == null || value.isEmpty() || value.replaceAll(" ", "").isEmpty()) return true;
        String param = getParam(key);
        if (param == null) return false;
        for (String item: param.split(",")) {
            if (item.equalsIgnoreCase(value)) return true;
        }
        return false;
    }

    public String getContext() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }

    public String getResourcesUrl() {
        String host = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("host");
        String protocol = host.contains("localhost:") ?"http://" :"https://";
        return protocol + host + getContext() + "/javax.faces.resources";
    }

    public String getResourcesForUrl(String name, String lib) {
        return getResourcesUrl() + "/" + name + ".xhtml?ln=" + lib;
    }

    public String getURL() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getRequestURL().toString();
    }
}