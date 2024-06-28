package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.CampusCodesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CampusRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CoursesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.UsersRepository;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.CampusCodes;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.util.List;

@Named("campusCtrl")
@ViewScoped
public class CampusCtrl extends AbstractSessionController {
    @Inject
    private CampusRepository campusRepository;
    @Inject
    private CampusCodesRepository campusCodesRepository;
    @Inject
    private UsersRepository usersRepository;
    @Inject
    private CoursesRepository coursesRepository;

    private Integer selectedCampusId;
    private Campus selectedCampus;
    private CampusCodes selectedCampusCodes;

    @Override
    public void init() {
        selectedCampus = null;
        selectedCampusCodes = null;
    }

    public void preUpdateOrCreate(Campus item) {
        if (item == null) selectedCampus = new Campus();
        else selectedCampus = item;
    }

    public void preUpdateCampusCode() {
        selectedCampusCodes = new CampusCodes();
    }

    public void update() {
        campusRepository.update(selectedCampus);
    }

    public void createAndCloseWVCampusCode(String modalWidgetVar, String updateForm) {
        if (validateIfExistCode(selectedCampusCodes.getCode())) {
            commonController.FacesMessagesError("Failed", "Keyword exists");
        } else {
            selectedCampusCodes.setCampusId(campusRepository.findById(selectedCampusId));
            campusCodesRepository.create(selectedCampusCodes);
            commonController.FacesMessagesInfo("Successful", "Create Item");
            PrimeFaces.current().executeScript("PF('" + modalWidgetVar + "').hide()");
            PrimeFaces.current().ajax().update(updateForm);
        }
    }

    private boolean validateIfExistCode(String code) {
        return campusCodesRepository.findByCode(code) != null;
    }

    public void removeCampusCode(CampusCodes campusCodes) {
        if (usersRepository.findByCampusCode(campusCodes.getId()) != null ||
                coursesRepository.findByCampusCode(campusCodes.getId()) != null) {
            commonController.FacesMessagesError("Failed", "Item is used");
        } else {
            campusCodesRepository.remove(campusCodes);
            commonController.FacesMessagesWarn("Successful", "Remove Item");
        }
    }

    public List<Campus> getAllCampus() {
        return campusRepository.findAll();
    }

    public List<CampusCodes> getAllCampusCodes() {
        return campusCodesRepository.findAllDesc();
    }

    public Campus getSelectedCampus() {
        return selectedCampus;
    }

    public void setSelectedCampus(Campus selectedCampus) {
        this.selectedCampus = selectedCampus;
    }

    public CampusCodes getSelectedCampusCodes() {
        return selectedCampusCodes;
    }

    public void setSelectedCampusCodes(CampusCodes selectedCampusCodes) {
        this.selectedCampusCodes = selectedCampusCodes;
    }

    public Integer getSelectedCampusId() {
        return selectedCampusId;
    }

    public void setSelectedCampusId(Integer selectedCampusId) {
        this.selectedCampusId = selectedCampusId;
    }
}
