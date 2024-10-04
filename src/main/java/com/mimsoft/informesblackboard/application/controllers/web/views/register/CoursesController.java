package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.models.lazy.CoursesLazyDataModel;
import com.mimsoft.informesblackboard.application.data.repositories.CampusRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CoursesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.GradesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.ModalityRepository;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.Courses;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import com.mimsoft.informesblackboard.domain.entities.Modality;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;

import java.util.List;

@Named("coursesCtrl")
@ViewScoped
public class CoursesController extends AbstractSessionController {
    @Inject
    private CoursesRepository coursesRepository;
    @Inject
    private GradesRepository gradesRepository;
    @Inject
    private ModalityRepository modalityRepository;
    @Inject
    private CampusRepository campusRepository;

    private LazyDataModel<Courses> coursesLazyDataModel;
    private Integer counterFilter;

    @Override
    public void init() {
        List<Courses> coursesList = coursesRepository.findAllDistinct();
        coursesLazyDataModel = new CoursesLazyDataModel(coursesList);
        counterFilter = coursesList.size();
    }

    public void updateCounter() {
        counterFilter = coursesLazyDataModel.getRowCount();
    }

    public List<Grades> getAllGrades() {
        return gradesRepository.findAll();
    }

    public List<Modality> getAllModalities() {
        return modalityRepository.findAll();
    }

    public List<Campus> getAllCampus() {
        return campusRepository.findAll();
    }

    public LazyDataModel<Courses> getCoursesLazyDataModel() {
        return coursesLazyDataModel;
    }

    public void setCoursesLazyDataModel(LazyDataModel<Courses> coursesLazyDataModel) {
        this.coursesLazyDataModel = coursesLazyDataModel;
    }

    public Integer getCounterFilter() {
        return counterFilter;
    }

    public void setCounterFilter(Integer counterFilter) {
        this.counterFilter = counterFilter;
    }
}
