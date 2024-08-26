package com.mimsoft.informesblackboard.application.controllers.web.views;

import com.mimsoft.informesblackboard.Configuration;
import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.FileStorageRepository;
import com.mimsoft.informesblackboard.application.modules.upload_execute.UploadExecuteService;
import com.mimsoft.informesblackboard.domain.entities.FileStorage;
import jakarta.annotation.Resource;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.transaction.UserTransaction;
import org.apache.commons.io.FileUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

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
        new Thread(() -> executeProcess(pathname, fileStorage.getCreatedAt(), uploadedFile.getContent())).start();
    }

    private void executeProcess(String pathname, Date date, byte[] data) {
        File file = new File(pathname);
        try {
            FileUtils.writeByteArrayToFile(file, data);
            if (selectedTypeFile.equalsIgnoreCase("users")) uploadExecuteService.executeProcessUsers(pathname, date, entityManager, userTransaction);
            else if (selectedTypeFile.equalsIgnoreCase("courses")) uploadExecuteService.executeProcessCourses(pathname, date, entityManager, userTransaction);
            //FileUtils.forceDelete(file);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                FileUtils.forceDeleteOnExit(file);
                fileStorageRepository.removeFromPathname(pathname);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
