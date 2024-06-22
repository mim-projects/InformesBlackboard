package com.mimsoft.boilerplate.application.utils.http.requests;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

public class MultipartForm {
    public static Integer getInt(MultipartFormDataInput input, String key) {
        try {
            String result = getString(input, key);
            if (result == null) return null;
            return Integer.parseInt(result);
        } catch (Exception ignore) {
            return null;
        }
    }

    public static String getString(MultipartFormDataInput input, String key) {
        try {
            return input.getFormDataMap().get(key).get(0).getBodyAsString();
        } catch (Exception ignore) {
            return null;
        }
    }

    public static byte[] getBytes(MultipartFormDataInput input, String key) {
        try {
            InputPart inputParts = input.getFormDataMap().get(key).get(0);
            InputStream inputStream = inputParts.getBody(InputStream.class, null);
            return IOUtils.toByteArray(inputStream);
        } catch (Exception ignore) {
            return null;
        }
    }

    public static MultipartFormModel getBytesList(MultipartFormDataInput input, String key) {
        MultipartFormModel multipartFormHelper = new MultipartFormModel();
        try {
            InputPart inputParts = input.getFormDataMap().get(key).get(0);
            String[] headers = inputParts.getHeaders().getFirst("Content-Disposition").split(";");
            System.out.println(Arrays.toString(headers));

            for (String section : headers) {
                if (section.trim().startsWith("filename")) {
                    String filename = section.trim().replaceAll(" ", "_").replace("-", "")
                            .replace("filename=", "");
                    filename = "file_" + new Date().getTime() + "_" + filename.substring(1, filename.length() - 1).toLowerCase();
                    String[] formatTemp = filename.split("\\.");
                    multipartFormHelper.setFilename(filename);
                    multipartFormHelper.setFormat(formatTemp[formatTemp.length - 1]);
                }
            }
            InputStream inputStream = inputParts.getBody(InputStream.class, null);
            multipartFormHelper.setBody(IOUtils.toByteArray(inputStream));
        } catch (Exception ignored) {
        }
        return multipartFormHelper;
    }
}