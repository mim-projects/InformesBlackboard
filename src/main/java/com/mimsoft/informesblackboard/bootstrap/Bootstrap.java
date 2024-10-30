package com.mimsoft.informesblackboard.bootstrap;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/api")
public class Bootstrap extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addApiResourceClasses(resources);
        addWebResourceClasses(resources);
        return resources;
    }

    private void addApiResourceClasses(Set<Class<?>> resources) {
    }

    private void addWebResourceClasses(Set<Class<?>> resources) {
    }
}