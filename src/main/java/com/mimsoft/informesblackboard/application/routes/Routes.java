package com.mimsoft.informesblackboard.application.routes;

public enum Routes {
    LOGOUT(new Route("logout")),
    HOME(new Route("home", "/app/home/index.xhtml")),
    UPLOADS(new Route("uploads", "/app/uploads.xhtml")),
    REGISTER_CAMPUS(new Route("register_campus", "/app/register/campus/index.xhtml")),
    REGISTER_CAMPUS_CODES(new Route("register_campus_codes", "/app/register/campus/codes.xhtml")),
    REGISTER_GRADES(new Route("register_grades", "/app/register/grades.xhtml")),
    REGISTER_MODALITY(new Route("register_modality", "/app/register/modality.xhtml")),
    REGISTER_COURSES(new Route("register_courses", "/app/register/courses.xhtml")),
    REGISTER_USERS(new Route("register_users", "/app/register/users/index.xhtml")),
    REGISTER_USERS_DETAILS(new Route("register_users_details", "/app/register/users/details.xhtml")),
    REGISTER_STORAGE(new Route("register_storage", "/app/register/storage.xhtml")),
    PLATFORM_ROLES(new Route("platform_roles", "/app/platform/roles.xhtml")),
    PLATFORM_USERS(new Route("platform_users", "/app/platform/users.xhtml")),
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
