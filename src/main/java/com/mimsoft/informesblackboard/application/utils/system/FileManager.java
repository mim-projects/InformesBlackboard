package com.mimsoft.informesblackboard.application.utils.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
    public static boolean Write(byte[] bytes, String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                if (!file.createNewFile()) return false;
            }
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(bytes);
            fop.flush();
            fop.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}