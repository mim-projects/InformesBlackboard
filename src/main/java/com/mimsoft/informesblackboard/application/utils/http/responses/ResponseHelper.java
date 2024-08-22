package com.mimsoft.informesblackboard.application.utils.http.responses;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseHelper {
    public static Response Error(Status status, String error) {
        return Response.ok(ResponseString.error(error)).status(status).build();
    }

    public static Response ErrorMessage(Status status, String error) {
        return Response.ok(ResponseString.errorMessage(error)).status(status).build();
    }

    public static Response SuccessMessage(String key, String content) {
        return Response.ok(ResponseString.response(key, content)).build();
    }

    public static Response.ResponseBuilder DisableCORS(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }

    public static void DownloadFile(FacesContext context, File file, String filename) throws IOException {
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.setHeader("Content-type", "application/octet-stream");
//        response.setHeader("Content-Length", String.valueOf(byteArrayOutputStream.size()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");;

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

    public static Response SuccessObject(String content) {
        return Response.ok(ResponseString.responseObject("data", content)).build();
    }

    public static Response SuccessMessage(String content) {
        return Response.ok(ResponseString.response("data", content)).build();
    }
}