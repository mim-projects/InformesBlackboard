package com.mimsoft.informesblackboard.application.modules.upload_execute.interfaces;

public interface ProcessLineCSV {
    void callback(Integer currentLine, Integer totalLine, String data) throws Exception;
}
