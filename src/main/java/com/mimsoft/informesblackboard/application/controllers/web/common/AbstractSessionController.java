package com.mimsoft.informesblackboard.application.controllers.web.common;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
