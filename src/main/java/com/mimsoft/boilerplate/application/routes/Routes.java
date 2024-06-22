package com.mimsoft.boilerplate.application.routes;

public enum Routes {
    LOGOUT(new Route("logout")),
    HOME(new Route("home", "/app/index.xhtml")),
    PROFILE(new Route("profile", "/app/profile.xhtml")),
    ;
    private final Route route;

    Routes(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }
}
