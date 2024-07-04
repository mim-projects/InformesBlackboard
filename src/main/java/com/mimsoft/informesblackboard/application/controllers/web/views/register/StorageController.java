package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.StorageHistoryRepository;
import com.mimsoft.informesblackboard.application.utils.others.DateHelper;
import com.mimsoft.informesblackboard.domain.entities.StorageHistory;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.util.List;

@Named("storageCtrl")
@ViewScoped
public class StorageController extends AbstractSessionController {
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
        if (storageHistoryRepository.findCreatedAt(selectedStorageHistory.getCreatedAt()) != null) {
            commonController.FacesMessagesError(sessionController.getBundleMessage("failed"), sessionController.getBundleMessage("keyword_exist"));
        } else {
            selectedStorageHistory.setCreatedAt(DateHelper.DateToStartMonth(selectedStorageHistory.getCreatedAt()));
            storageHistoryRepository.create(selectedStorageHistory);
            commonController.FacesMessagesInfo(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("create_item"));
            PrimeFaces.current().executeScript("PF('" + modalWidgetVar + "').hide()");
            PrimeFaces.current().ajax().update(updateForm);
        }
    }

    public void updateAndCloseWV(String modalWidgetVar, String updateForm) {
        storageHistoryRepository.update(selectedStorageHistory);
        commonController.FacesMessagesInfo(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("update_item"));
        PrimeFaces.current().executeScript("PF('" + modalWidgetVar + "').hide()");
        PrimeFaces.current().ajax().update(updateForm);
    }

    public void remove(StorageHistory item) {
        storageHistoryRepository.remove(item);
        commonController.FacesMessagesWarn(sessionController.getBundleMessage("successful"), sessionController.getBundleMessage("remove_item"));
    }

    public List<StorageHistory> getAllStorageHistory() {
        return storageHistoryRepository.findAllDesc();
    }

    public StorageHistory getSelectedStorageHistory() {
        return selectedStorageHistory;
    }

    public void setSelectedStorageHistory(StorageHistory selectedStorageHistory) {
        this.selectedStorageHistory = selectedStorageHistory;
    }
}
