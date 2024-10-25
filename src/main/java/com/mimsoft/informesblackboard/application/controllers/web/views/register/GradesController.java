package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.GradesRepository;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.List;

@Named("gradesCtrl")
@ViewScoped
public class GradesController extends AbstractSessionController {

    @Inject
    private GradesRepository gradesRepository;

    private List<Grades> grades;

    @Override
    public void init() {
        grades = gradesRepository.findAll();
    }

    public List<Grades> getAllGrades() {
        return gradesRepository.findAll();
    }

    public List<Grades> getGrades() {
        return grades;
    }

    public void setGrades(List<Grades> grades) {
        this.grades = grades;
    }

}
