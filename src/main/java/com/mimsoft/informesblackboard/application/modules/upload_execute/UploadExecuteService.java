package com.mimsoft.informesblackboard.application.modules.upload_execute;

import com.mimsoft.informesblackboard.Configuration;
import com.mimsoft.informesblackboard.application.modules.upload_execute.interfaces.ProcessLineCSV;
import com.mimsoft.informesblackboard.application.modules.upload_execute.interfaces.UploadExecuteCycle;
import com.mimsoft.informesblackboard.application.modules.upload_execute.services.ProcessCoursesServices;
import com.mimsoft.informesblackboard.application.modules.upload_execute.services.ProcessUsersServices;
import com.mimsoft.informesblackboard.application.utils.system.FileManager;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.transaction.UserTransaction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@TransactionManagement(TransactionManagementType.BEAN)
public class UploadExecuteService {
    @Inject
    private ProcessUsersServices processUsersServices;
    @Inject
    private ProcessCoursesServices processCoursesServices;

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

    private synchronized void processCycle(String path, UploadExecuteCycle uploadExecuteCycle) throws Exception {
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
    public synchronized void executeProcessUsers(String path, UserTransaction userTransaction) throws Exception {
        processUsersServices.load();
        userTransaction.setTransactionTimeout(Configuration.TRANSACTION_TIME_SQL);
        process(path, (currentLine, totalLine, data) -> processUsersServices.execute(currentLine, totalLine, data));
    }

    @Lock(LockType.READ)
    public synchronized void executeProcessCourses(String path, UserTransaction userTransaction) throws Exception {
        processCoursesServices.load();
        userTransaction.setTransactionTimeout(Configuration.TRANSACTION_TIME_SQL);
        process(path, (currentLine, totalLine, data) -> processCoursesServices.execute(currentLine, totalLine, data));
    }
}
