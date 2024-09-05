package com.mimsoft.informesblackboard.application.routes;

import com.mimsoft.informesblackboard.application.data.constants.RolesPlatform;

public class Route {
    private String name;
    private String url;
    private RolesPlatform roles;

    public Route(String name) {
        this.name = name;
        this.url = "";
        this.roles = null;
    }

    public Route(String name, String url) {
        this.name = name;
        this.url = url;
        this.roles = null;
    }

    public Route(String name, String url, RolesPlatform roles) {
        this.name = name;
        this.url = url;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RolesPlatform getRoles() {
        return roles;
    }

    public void setRoles(RolesPlatform roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Route{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", roles=" + roles +
                '}';
    }
}
