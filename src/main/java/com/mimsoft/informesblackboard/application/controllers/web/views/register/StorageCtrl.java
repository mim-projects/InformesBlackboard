package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.StorageHistoryRepository;
import com.mimsoft.informesblackboard.domain.entities.StorageHistory;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.util.List;

@Named("storageCtrl")
@ViewScoped
public class StorageCtrl extends AbstractSessionController {
    @Inject
    private StorageHistoryRepository storageHistoryRepository;

    private StorageHistory selectedStorageHistory;

    @Override
    public void init() {
        selectedStorageHistory = null;
    }

    public void preUpdateOrCreate(StorageHistory item) {
        if (item == null) {
            selectedStorageHistory = new StorageHistory();
            selectedStorageHistory.setValue(0f);
        } else {
            selectedStorageHistory = item;
        }
    }

    public void createAndCloseWV(String modalWidgetVar, String updateForm) {
        if (validateIfExistKeyword(selectedStorageHistory)) {
            commonController.FacesMessagesError("Failed", "Keyword exists");
        } else {
            storageHistoryRepository.create(selectedStorageHistory);
            commonController.FacesMessagesInfo("Successful", "Create Item");
            PrimeFaces.current().executeScript("PF('" + modalWidgetVar + "').hide()");
            PrimeFaces.current().ajax().update(updateForm);
        }
    }

    public void updateAndCloseWV(String modalWidgetVar, String updateForm) {
        if (validateIfExistKeyword(selectedStorageHistory)) {
            commonController.FacesMessagesError("Failed", "Keyword exists");
        } else {
            storageHistoryRepository.update(selectedStorageHistory);
            commonController.FacesMessagesInfo("Successful", "Update Item");
            PrimeFaces.current().executeScript("PF('" + modalWidgetVar + "').hide()");
            PrimeFaces.current().ajax().update(updateForm);
        }
    }

    private boolean validateIfExistKeyword(StorageHistory item) {
        StorageHistory result = storageHistoryRepository.findKeyword(item.getKeyword());
        if (result == null) return false;
        return item.getId() == null || !result.getKeyword().equalsIgnoreCase(item.getKeyword());
    }

    public void remove(StorageHistory item) {
        storageHistoryRepository.remove(item);
    }

    public List<StorageHistory> getAllStorageHistory() {
        return storageHistoryRepository.findAll();
    }

    public StorageHistory getSelectedStorageHistory() {
        return selectedStorageHistory;
    }

    public void setSelectedStorageHistory(StorageHistory selectedStorageHistory) {
        this.selectedStorageHistory = selectedStorageHistory;
    }
}
