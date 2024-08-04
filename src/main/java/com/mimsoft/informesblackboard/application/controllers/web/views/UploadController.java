package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.Configuration;
import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.modules.upload_execute.UploadExecuteService;
import jakarta.annotation.Resource;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.UserTransaction;
import org.apache.commons.io.FileUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.File;
import java.util.Date;

@Named("uploadCtrl")
@ViewScoped
public class UploadController extends AbstractSessionController {
    @Resource
    protected UserTransaction userTransaction;
    @Inject
    private UploadExecuteService uploadExecuteService;

    private String selectedTypeFile;

    @Override
    public void init() {
        selectedTypeFile = "users";
    }

    //  > http://localhost:9990/console/index.html#undertow-server;name=default-server
    //  > Listener > HTTP > Max Post Size = 104857600 | (100 MB)
    public synchronized void handleFileUpload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        String[] temp = uploadedFile.getFileName().split("\\.");
        String filename = ("FILE_" + new Date().getTime() + "." + temp[temp.length - 1]).toUpperCase();
        new Thread(() -> executeProcess(filename, uploadedFile.getContent())).start();
    }

    private void executeProcess(String filename, byte[] data) {
        try {
            String pathname = Configuration.PATH_FILE_UPLOADS + filename;
            File file = new File(pathname);
            FileUtils.writeByteArrayToFile(file, data);
            if (selectedTypeFile.equalsIgnoreCase("users")) uploadExecuteService.executeProcessUsers(pathname, userTransaction);
            else if (selectedTypeFile.equalsIgnoreCase("courses")) uploadExecuteService.executeProcessCourses(pathname, userTransaction);
            FileUtils.forceDelete(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeComplete() {
        try {
            PrimeFaces.current().ajax().addCallbackParam("data", "{\"complete\":" + uploadExecuteService.isProcessComplete() + ", \"progress\":\"" + uploadExecuteService.getProgress() + "\"}");
            if (uploadExecuteService.isProcessComplete() != null && uploadExecuteService.isProcessComplete()) uploadExecuteService.restart();
        } catch (Exception e) {
            PrimeFaces.current().ajax().addCallbackParam("data", "{\"complete\": null, \"progress\":\"0\"}");
            uploadExecuteService.restart();
        }
    }

    public void updateSelectedTypeFile(String selectedTypeFile) {
        this.selectedTypeFile = selectedTypeFile;
    }

    public String getSelectedTypeFile() {
        return selectedTypeFile;
    }
}
