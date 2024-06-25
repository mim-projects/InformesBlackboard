package com.mimsoft.informesblackboard.application.routes;

public enum Routes {
    LOGOUT(new Route("logout")),
    HOME(new Route("home", "/app/index.xhtml")),
    UPLOADS(new Route("uploads", "/app/uploads.xhtml")),
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
