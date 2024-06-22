package com.mimsoft.informesblackboard.application.utils.system;

import java.io.*;

public class FileManager {
    public interface IReadFileForLine {
        void readLine(Integer index, String content);
    }

    public static void ReadLine(String path, IReadFileForLine readFileForLine) throws Exception {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        int row = 0;
        String line;
        while ((line = br.readLine()) != null) {
            readFileForLine.readLine(row, line);
            row++;
        }
        br.close();
        fis.close();
    }

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