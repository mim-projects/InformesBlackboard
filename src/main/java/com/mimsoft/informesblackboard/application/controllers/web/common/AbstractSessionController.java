package com.mimsoft.informesblackboard.application.controllers.web.common;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import java.io.Serializable;

public abstract class AbstractSessionController implements Serializable {
    @Inject
    protected SessionController sessionController;
    @Inject
    protected CommonController commonController;

    @PostConstruct()
    public void preInit() {
        if (!sessionController.isActive()) {
            sessionController.logout();
            return;
        }
        init();
    }

    public abstract void init();
}
