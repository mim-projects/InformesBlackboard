package com.mimsoft.informesblackboard.application.modules.upload_execute;

import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheKeywords;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheServices;
import com.mimsoft.informesblackboard.application.modules.upload_execute.interfaces.ProcessLineCSV;
import com.mimsoft.informesblackboard.application.modules.upload_execute.interfaces.UploadExecuteCycle;
import com.mimsoft.informesblackboard.application.modules.upload_execute.services.ProcessCoursesServices;
import com.mimsoft.informesblackboard.application.modules.upload_execute.services.ProcessMassiveServices;
import com.mimsoft.informesblackboard.application.modules.upload_execute.services.ProcessUsersServices;
import com.mimsoft.informesblackboard.application.utils.system.FileManager;
import com.mimsoft.informesblackboard.domain.entities.Courses;
import com.mimsoft.informesblackboard.domain.entities.Users;
import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class UploadExecuteService {
    @Inject
    private ProcessUsersServices processUsersServices;
    @Inject
    private ProcessCoursesServices processCoursesServices;
    @Inject
    private SimulateCacheServices simulateCacheServices;

    private Boolean processComplete = null;
    private int progress = 0;

    @Lock(LockType.READ)
    public Boolean isProcessComplete() {
        return processComplete;
    }

    @Lock(LockType.READ)
    public int getProgress() {
        return progress;
    }

    @Lock(LockType.WRITE)
    private void setProgress(int progress) {
        this.progress = progress;
    }

    @Lock(LockType.WRITE)
    public void restart() {
        processComplete = null;
        setProgress(0);
    }

    private void processCycle(String path, UploadExecuteCycle uploadExecuteCycle) throws Exception {
        if (processComplete != null && processComplete) throw new IllegalStateException("processComplete has already been called");
        try {
            processComplete = false;
            progress = 0;
            uploadExecuteCycle.callback();
            processComplete = true;
            progress = 100;
        } catch (Exception e) {
            restart();
            throw new Exception("Failed Process File: " + path);
        }
    }

    private synchronized void process(String pathFile, ProcessLineCSV processLineCSV) throws Exception {
        Path path = Paths.get(pathFile);
        int totalLines = (int) Files.lines(path).count();
        processCycle(pathFile, () -> FileManager.ReadLine(pathFile, (currentLine, content) -> {
            float percentage = (float) currentLine / totalLines;
            setProgress((int) (percentage * 100));
            processLineCSV.callback(currentLine, totalLines, content);
        }));
    }

    @Lock(LockType.READ)
    public void executeProcessUsers(String path, Date date, EntityManager entityManager, UserTransaction userTransaction) throws Exception {
        processUsersServices.load(date);
        ProcessMassiveServices<Users> processMassiveServices = new ProcessMassiveServices<>(entityManager, userTransaction);
        process(path, (currentLine, totalLine, data) -> processUsersServices.execute(currentLine, totalLine, data, processMassiveServices::addQueryString));
        processMassiveServices.executeString();
        simulateCacheServices.remove(SimulateCacheKeywords.AllPeriods.getKeyword());
    }

    @Lock(LockType.READ)
    public void executeProcessCourses(String path, Date date, EntityManager entityManager, UserTransaction userTransaction) throws Exception {
        processCoursesServices.load(date);
        ProcessMassiveServices<Courses> processMassiveServices = new ProcessMassiveServices<>(entityManager, userTransaction);
        process(path, (currentLine, totalLine, data) -> processCoursesServices.execute(currentLine, totalLine, data, processMassiveServices::add));
        processMassiveServices.execute();
        simulateCacheServices.remove(SimulateCacheKeywords.AllPeriods.getKeyword());
    }
}
