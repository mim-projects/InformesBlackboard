package com.mimsoft.informesblackboard.application.utils.http;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseHelper {
    public interface OutputStreamHelper {
        void stream(OutputStream outputStream) throws IOException;
    }

    public static void DownloadFile(FacesContext context, String filename, File file) throws IOException {
        HttpServletResponse response = GetHttpServletResponse(context, filename);
        OutputStream outputStream = response.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] bytesBuffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) { outputStream.write(bytesBuffer, 0, bytesRead); }

        outputStream.flush();
        fileInputStream.close();
        outputStream.close();
        context.responseComplete();
    }

    public static void DownloadOutputStream(FacesContext context, String filename, OutputStreamHelper outputStreamHelper) throws IOException {
        try {
            HttpServletResponse response = GetHttpServletResponse(context, filename);

            OutputStream outputStream = response.getOutputStream();
            outputStreamHelper.stream(outputStream);

            outputStream.flush();
            outputStream.close();
            context.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HttpServletResponse GetHttpServletResponse(FacesContext context, String filename) {
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.setHeader("Content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        return response;
    }
}