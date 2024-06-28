package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.GradesRepository;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("gradesCtrl")
@ViewScoped
public class GradesCtrl extends AbstractSessionController {
    @Inject
    private GradesRepository gradesRepository;

    @Override
    public void init() {

    }

    public List<Grades> getAllGrades() {
        return gradesRepository.findAll();
    }
}
