package com.mimsoft.boilerplate.application.routes;

public class Route {
    private String name;
    private String url;
    private String roles;

    public Route(String name) {
        this.name = name;
        this.url = "";
        this.roles = "";
    }

    public Route(String name, String url) {
        this.name = name;
        this.url = url;
        this.roles = "";
    }

    public Route(String name, String url, String roles) {
        this.name = name;
        this.url = url;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Route{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
