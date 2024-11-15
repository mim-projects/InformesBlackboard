package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.Configuration;
import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.FileStorageRepository;
import com.mimsoft.informesblackboard.application.modules.upload_execute.UploadExecuteService;
import com.mimsoft.informesblackboard.domain.entities.FileStorage;
import org.apache.commons.io.FileUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.Resource;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named("uploadCtrl")
@ViewScoped
public class UploadController extends AbstractSessionController {
    @Inject
    protected EntityManager entityManager;
    @Resource
    protected UserTransaction userTransaction;
    @Inject
    private UploadExecuteService uploadExecuteService;
    @Inject
    private FileStorageRepository fileStorageRepository;

    private String selectedTypeFile;

    @Override
    public void init() {
        selectedTypeFile = "users";
    }

    private boolean validateFileFormatName(String filename) {
        String regex = "[0-9]{5}_[A-Za-z]+_[A-Za-z]+ \\([0-9]{2}-[0-9]{2}-[0-9]{2}\\).(txt|csv|tsv)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filename);
        return matcher.matches();
    }

    public synchronized void handleFileUpload(FileUploadEvent event) {
        String originalName = event.getFile().getFileName();
        if (fileStorageRepository.exists(originalName)) {
            commonController.FacesMessagesError("Failed", "File exist");
            return;
        }

        if (!validateFileFormatName(originalName)) {
            commonController.FacesMessagesError("Failed", "File name invalid format");
            return;
        }

        UploadedFile uploadedFile = event.getFile();
        String[] temp = uploadedFile.getFileName().split("\\.");
        String filename = ("FILE_" + new Date().getTime() + "." + temp[temp.length - 1]).toUpperCase();
        String pathname = Configuration.PATH_FILE_UPLOADS + filename;

        try {
            FileUtils.writeByteArrayToFile(new File(pathname), uploadedFile.getContent());
        } catch (IOException e) {
            commonController.FacesMessagesError("Failed", "Could not be saved to the server");
            return;
        }

        Date date = new Date();
        String[] parts = originalName.split(" ");
        if (parts.length > 1) {
            String year = parts[0].substring(0, 4);
            String monthDay = parts[1].substring(1, 9);
            String format = year + "-" + monthDay;
            try { date = new SimpleDateFormat("yyyy-MM-dd-HH").parse(format); }
            catch (ParseException ignore) { }
        }

        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(originalName);
        fileStorage.setPath(pathname);
        fileStorage.setCreatedAt(date);
        fileStorageRepository.create(fileStorage);

        uploadExecuteService.preExecute(pathname, date, entityManager, userTransaction);
        executeProcess(pathname);
    }

    private void executeProcess(String pathname) {
        new Thread(() -> {
            try {
                if (selectedTypeFile.equalsIgnoreCase("users")) uploadExecuteService.executeProcessUsers();
                else if (selectedTypeFile.equalsIgnoreCase("courses")) uploadExecuteService.executeProcessCourses();
                //FileUtils.forceDelete(file);
            } catch (Exception e) {
                fileStorageRepository.removeFromPathname(pathname);
                e.printStackTrace();
            }
        }).start();
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
