package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.CampusCodesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CampusRepository;
import com.mimsoft.informesblackboard.application.data.repositories.CoursesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.UsersRepository;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.CampusCodes;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
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
    private Integer counterFilter;
    // Variables para almacenar el campo de orden y el orden ascendente/descendente
    private String sortField;
    private boolean ascending = true;
    private List<Campus> campus;
    private List<CampusCodes>campuscode;

    @Override

    public void init() {
        selectedCampus = null;
        selectedCampusCodes = null;
        counterFilter = 0;
        campus = campusRepository.findAll();
        campuscode=campusCodesRepository.findAll();
    }

    public void updateCampusList() {
        // Aquí puedes realizar la lógica para actualizar la lista de campus
        // campusList = service.getCampusOrderedBy(selectedColumn);
    }

    public void preUpdateOrCreate(Campus item) {
        if (item == null) {
            selectedCampus = new Campus();
        } else {
            selectedCampus = item;
        }
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
            commonController.FacesMessagesWarn(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("remove_item"));
        }
    }

    public List<Campus> getAllCampus() {
        return campusRepository.findAll();
    }
    // Método que devuelve la lista ordenada

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

    public Integer getCounterFilter() {
        return counterFilter;
    }

    public void setCounterFilter(Integer counterFilter) {
        this.counterFilter = counterFilter;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public List<Campus> getCampus() {
        return campus;
    }

    public void setCampus(List<Campus> campus) {
        this.campus = campus;
    }

    public List<CampusCodes> getCampuscode() {
        return campuscode;
    }

    public void setCampuscode(List<CampusCodes> campuscode) {
        this.campuscode = campuscode;
    }

}
