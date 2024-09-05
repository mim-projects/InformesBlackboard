package com.mimsoft.informesblackboard.application.modules.reports.utils;

import java.io.OutputStream;

public interface TemplateInstance {
    void render(OutputStream outputStream);
}
