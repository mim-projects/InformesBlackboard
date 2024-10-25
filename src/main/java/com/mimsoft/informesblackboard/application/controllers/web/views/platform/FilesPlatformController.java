package com.mimsoft.informesblackboard.application.controllers.web.views.platform;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.FileStorageRepository;
import com.mimsoft.informesblackboard.application.utils.http.ResponseHelper;
import com.mimsoft.informesblackboard.domain.entities.FileStorage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Named("filesPlatformCtrl")
@ViewScoped
public class FilesPlatformController extends AbstractSessionController {

    @Inject
    private FileStorageRepository fileStorageRepository;

    private List<FileStorage> filestorage;

    @Override
    public void init() {
        filestorage = fileStorageRepository.getAll();
    }
    
    

    public void download(FileStorage fileStorage) {
        File file = new File(fileStorage.getPath());
        try {
            ResponseHelper.DownloadFile(FacesContext.getCurrentInstance(), fileStorage.getName(), file);
        } catch (IOException ignore) {
            commonController.FacesMessagesError("Failed", "Download Failed");
        }
    }

    public List<FileStorage> getAllFiles() {
        return fileStorageRepository.getAll();
    }

    public void remove(FileStorage fileStorage) {
        fileStorageRepository.remove(fileStorage);
    }

    public List<FileStorage> getFilestorage() {
        return filestorage;
    }

    public void setFilestorage(List<FileStorage> filestorage) {
        this.filestorage = filestorage;
    }

}
