package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.CampusCodesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CampusRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CoursesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.UsersRepository;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.CampusCodes;
import org.primefaces.PrimeFaces;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("campusCtrl")
@ViewScoped
public class CampusController extends AbstractSessionController {
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
    private List<Campus> allCampus;
    private List<CampusCodes> allCampusCodes;

    @Override
    public void init() {
        selectedCampus = null;
        selectedCampusCodes = null;
        allCampus = campusRepository.findAll();
        allCampusCodes = campusCodesRepository.findAllDesc();
    }

    public void preUpdateOrCreate(Campus item) {
        selectedCampus = item == null ? new Campus() : item;
    }

    public void preUpdateCampusCode() {
        selectedCampusCodes = new CampusCodes();
    }

    public void update() {
        campusRepository.update(selectedCampus);
    }

    public void createAndCloseWVCampusCode(String modalWidgetVar, String updateForm) {
        if (validateIfExistCode(selectedCampusCodes.getCode())) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("keyword_exist"));
        } else {
            selectedCampusCodes.setCampusId(campusRepository.findById(selectedCampusId));
            campusCodesRepository.create(selectedCampusCodes);
            commonController.FacesMessagesInfo(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("create_item"));
            init();
            PrimeFaces.current().executeScript("PF('" + modalWidgetVar + "').hide()");
            PrimeFaces.current().ajax().update(updateForm);
        }
    }

    private boolean validateIfExistCode(String code) {
        return campusCodesRepository.findByCode(code) != null;
    }

    public void removeCampusCode(CampusCodes campusCodes) {
        if (usersRepository.findByCampusCode(campusCodes.getId()) != null
                || coursesRepository.findByCampusCode(campusCodes.getId()) != null) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("keyword_exist"));
        } else {
            campusCodesRepository.remove(campusCodes);
            init();
            commonController.FacesMessagesWarn(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("remove_item"));
        }
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

    public List<Campus> getAllCampus() {
        return allCampus;
    }

    public void setAllCampus(List<Campus> allCampus) {
        this.allCampus = allCampus;
    }

    public List<CampusCodes> getAllCampusCodes() {
        return allCampusCodes;
    }

    public void setAllCampusCodes(List<CampusCodes> allCampusCodes) {
        this.allCampusCodes = allCampusCodes;
    }
}
