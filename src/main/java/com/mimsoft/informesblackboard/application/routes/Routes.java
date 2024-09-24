package com.mimsoft.informesblackboard.application.routes;

import com.mimsoft.informesblackboard.application.data.constants.RolesPlatform;

public enum Routes {
    ERROR_404(new Route("error_404", "/error/_404.xhtml")),
    LOGOUT(new Route("logout")),
    PROFILE(new Route("profile", "/app/profile.xhtml")),
    HOME(new Route("home", "/app/home/index.xhtml")),
    UPLOADS(new Route("uploads", "/app/uploads.xhtml", RolesPlatform.ADMIN)),
    REGISTER_CAMPUS(new Route("register_campus", "/app/register/campus/index.xhtml", RolesPlatform.ADMIN)),
    REGISTER_CAMPUS_CODES(new Route("register_campus_codes", "/app/register/campus/codes.xhtml", RolesPlatform.ADMIN)),
    REGISTER_GRADES(new Route("register_grades", "/app/register/grades.xhtml", RolesPlatform.ADMIN)),
    REGISTER_PERIODS(new Route("register_periods", "/app/register/periods.xhtml", RolesPlatform.ADMIN)),
    REGISTER_MODALITY(new Route("register_modality", "/app/register/modality.xhtml", RolesPlatform.ADMIN)),
    REGISTER_COURSES(new Route("register_courses", "/app/register/courses.xhtml", RolesPlatform.ADMIN)),
    REGISTER_USERS(new Route("register_users", "/app/register/users/index.xhtml", RolesPlatform.ADMIN)),
    REGISTER_USERS_DETAILS(new Route("register_users_details", "/app/register/users/details.xhtml", RolesPlatform.ADMIN)),
    REGISTER_STORAGE(new Route("register_storage", "/app/register/storage.xhtml", RolesPlatform.ADMIN)),
    PLATFORM_ROLES(new Route("platform_roles", "/app/platform/roles.xhtml", RolesPlatform.ADMIN)),
    PLATFORM_USERS(new Route("platform_users", "/app/platform/users.xhtml", RolesPlatform.ADMIN)),
    PLATFORM_CACHE(new Route("platform_cache", "/app/platform/cache.xhtml", RolesPlatform.ADMIN)),
    PLATFORM_FILES(new Route("platform_files", "/app/platform/files.xhtml", RolesPlatform.ADMIN)),
    ;
    private final Route route;

    Routes(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }
}
